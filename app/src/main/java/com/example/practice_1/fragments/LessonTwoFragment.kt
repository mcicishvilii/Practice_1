package com.example.practice_1.fragments

import android.annotation.SuppressLint
import android.content.*
import android.database.sqlite.SQLiteDatabase
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_1.MusicDatabaseHelper
import com.example.practice_1.R
import com.example.practice_1.adapters.SongsAdapter
import com.example.practice_1.common.BaseFragment
import com.example.practice_1.data.Song
import com.example.practice_1.databinding.FragmentLessonTwoBinding
import com.example.practice_1.services.MusicService
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class LessonTwoFragment :
    BaseFragment<FragmentLessonTwoBinding>(FragmentLessonTwoBinding::inflate) {

    private val songsAdapter: SongsAdapter by lazy { SongsAdapter() }
    private val songsList = mutableListOf<Song>()

    private lateinit var musicDatabaseHelper: MusicDatabaseHelper
    private lateinit var db: SQLiteDatabase

    private var path: Int? = null

    private lateinit var musicService: MusicService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as MusicService.MusicServiceBinder
            musicService = binder.getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        musicDatabaseHelper = MusicDatabaseHelper(context)
        db = musicDatabaseHelper.writableDatabase
    }

    override fun onStart() {
        super.onStart()
        Intent(requireContext(), MusicService::class.java).also {
            requireActivity().bindService(it, connection, Context.BIND_AUTO_CREATE)
            requireActivity().startService(it)
        }
    }


    @SuppressLint("Range")
    override fun viewCreated() {


        setupRecycler()
        selectSong()

        val projection = arrayOf(
            MusicDatabaseHelper.COLUMN_TITLE,
            MusicDatabaseHelper.COLUMN_ARTIST,
            MusicDatabaseHelper.COLUMN_GENRE,
            MusicDatabaseHelper.COLUMN_PATH
        )

        val sortOrder = "${MusicDatabaseHelper.COLUMN_TITLE} ASC"

        val cursor = db.query(
            MusicDatabaseHelper.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        while (cursor.moveToNext()) {
            val title =
                cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabaseHelper.COLUMN_TITLE))
            val artist =
                cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabaseHelper.COLUMN_ARTIST))
            val genre =
                cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabaseHelper.COLUMN_GENRE))
            val path = cursor.getInt(cursor.getColumnIndexOrThrow(MusicDatabaseHelper.COLUMN_PATH))

            songsList.add(Song(path, title, genre, artist))

            songsAdapter.submitList(songsList)

            songsAdapter.apply {
                binding.spinnerGenreImpl.setOnDismissListener {
                    val genreFromSpinner = binding.spinnerGenreImpl.text.toString()
                    if(genreFromSpinner != "All"){
                        filterSongs(genreFromSpinner)
                    }else{
                        songsAdapter.submitList(songsList)
                    }
                }

                binding.spinnerArtistImpl.setOnDismissListener {
                    val artistFromSPinner = binding.spinnerArtistImpl.text.toString()
                    if(artistFromSPinner != "All"){
                        filterSongs(artistFromSPinner)
                    }else{
                        songsAdapter.submitList(songsList)
                    }
                }
            }
        }
        cursor.close()
        setupArtistSpinner()
        setupGenreSpinner()
    }


    override fun listeners() {
        val genre = binding.spinnerGenreImpl.text.toString()
        val artist = binding.spinnerArtistImpl.text.toString()

            Log.d("GESTAPO",genre)

        play()
        stop()
        pause()
    }


    private fun filterSongs(query: String) {
        val filteredSongs = songsList.filter {
            it.songGenre.contains(query, true) || it.songArtist.contains(query,true)}
        songsAdapter.submitList(filteredSongs)
    }
    private fun setupGenreSpinner() {
        val genre = resources.getStringArray(R.array.genre)
        val adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_layout, genre)
        binding.spinnerGenreImpl.setAdapter(adapter)
    }

    private fun setupArtistSpinner() {
        val artist = resources.getStringArray(R.array.artist)
        val adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_layout, artist)
        binding.spinnerArtistImpl.setAdapter(adapter)
    }
    private fun play() {
        binding.btnPlay.setOnClickListener {
            if (::musicService.isInitialized) {
                musicService.playMusic(path!!)
            }
        }
    }

    private fun stop() {
        binding.btnStop.setOnClickListener {
            if (::musicService.isInitialized) {
                musicService.stopMusic()
            }
        }
    }

    private fun pause() {
        binding.btnPause.setOnClickListener {
            if (::musicService.isInitialized) {
                musicService.pauseMusic()
            }
        }
    }

    private fun addSong() {
        val values = ContentValues().apply {
            put(MusicDatabaseHelper.COLUMN_TITLE, "Otherside")
            put(MusicDatabaseHelper.COLUMN_ARTIST, "rhcp")
            put(MusicDatabaseHelper.COLUMN_GENRE, "alternative rock")
            put(
                MusicDatabaseHelper.COLUMN_PATH,
                R.raw.red_hot_chili_peppers_otherside_official_music_
            )
        }

        db.insert(MusicDatabaseHelper.TABLE_NAME, null, values)
    }
    private fun selectSong(){
        songsAdapter.apply {
            setOnItemClickListener { song, _ ->
                Log.d("GESTAPO", song.song.toString())
                path = song.song
                binding.apply {
                    tvSongName.text = song.songTitle
                    tvArtist.text = song.songArtist
                    tvGenre.text = song.songGenre
                }
            }

            setOnDeleteClickListener { song, i ->
                db.delete(MusicDatabaseHelper.TABLE_NAME, null, null)
            }
        }
    }


    private fun setupRecycler() {
        binding.rvSongs.apply {
            adapter = songsAdapter
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        musicDatabaseHelper.close()
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unbindService(connection)
    }


}
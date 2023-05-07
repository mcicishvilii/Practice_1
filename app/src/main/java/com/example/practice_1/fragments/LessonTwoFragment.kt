package com.example.practice_1.fragments

import android.annotation.SuppressLint
import android.content.*
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_1.MusicDatabaseHelper
import com.example.practice_1.R
import com.example.practice_1.adapters.ItemsAdapter
import com.example.practice_1.adapters.SongsAdapter
import com.example.practice_1.common.BaseFragment
import com.example.practice_1.data.Song
import com.example.practice_1.databinding.FragmentLessonTwoBinding
import com.example.practice_1.services.MusicService

class LessonTwoFragment :
    BaseFragment<FragmentLessonTwoBinding>(FragmentLessonTwoBinding::inflate) {

    private val songsAdapter: SongsAdapter by lazy { SongsAdapter() }
    val songsList = mutableListOf<Song>()

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

    override fun onStop() {
        super.onStop()
        requireActivity().unbindService(connection)
    }

    @SuppressLint("Range")
    override fun viewCreated() {


        setupRecycler()

        songsAdapter.apply {
            setOnItemClickListener { song, _ ->
                Log.d("GESTAPO", song.song.toString())
                path = song.song
            }
        }

        val values = ContentValues().apply {
            put(MusicDatabaseHelper.COLUMN_TITLE, "Californication")
            put(MusicDatabaseHelper.COLUMN_ARTIST, "rhcp")
            put(MusicDatabaseHelper.COLUMN_GENRE, "sampli kide axali")
            put(MusicDatabaseHelper.COLUMN_PATH, R.raw.rhcp1)
        }

//        db.insert(MusicDatabaseHelper.TABLE_NAME, null, values)

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
            val title = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabaseHelper.COLUMN_TITLE))
            val artist = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabaseHelper.COLUMN_ARTIST))
            val genre = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabaseHelper.COLUMN_GENRE))
            val path = cursor.getInt(cursor.getColumnIndexOrThrow(MusicDatabaseHelper.COLUMN_PATH))
            songsList.add(Song(path, title, genre, artist))
            songsAdapter.apply {
                submitList(songsList)
            }
        }
        cursor.close()
    }

    override fun listeners() {
        play()
        stop()
        pause()
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


}
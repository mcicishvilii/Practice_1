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
import com.example.practice_1.MusicDatabaseHelper
import com.example.practice_1.R
import com.example.practice_1.common.BaseFragment
import com.example.practice_1.data.Song
import com.example.practice_1.databinding.FragmentLessonTwoBinding
import com.example.practice_1.services.MusicService

class LessonTwoFragment :
    BaseFragment<FragmentLessonTwoBinding>(FragmentLessonTwoBinding::inflate) {



    private lateinit var musicDatabaseHelper: MusicDatabaseHelper
    private lateinit var db: SQLiteDatabase
    override fun onAttach(context: Context) {
        super.onAttach(context)
        musicDatabaseHelper = MusicDatabaseHelper(context)
        db = musicDatabaseHelper.writableDatabase
    }
    @SuppressLint("Range")
    override fun viewCreated() {

        val uri = Uri.parse("content://com.example.practice_1.musicprovider/music")

        val values = ContentValues().apply {
            put(MusicDatabaseHelper.COLUMN_TITLE, "Californication")
            put(MusicDatabaseHelper.COLUMN_ARTIST, "rhcp")
            put(MusicDatabaseHelper.COLUMN_GENRE, "alternative")
            put(MusicDatabaseHelper.COLUMN_PATH, "raw/rhcp.mp4")
        }

        db.insert(MusicDatabaseHelper.TABLE_NAME, null, values)


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
            val path = cursor.getString(cursor.getColumnIndexOrThrow(MusicDatabaseHelper.COLUMN_PATH))
            Log.d("Song", "$title - $artist - $genre - $path")
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
            val intent = Intent(requireContext(), MusicService::class.java)
            intent.action = MusicService.ACTION_PLAY
            requireActivity().startService(intent)
        }
    }
    private fun stop() {
        binding.btnStop.setOnClickListener{
            val intent = Intent(requireContext(), MusicService::class.java)
            intent.action = MusicService.ACTION_STOP
            requireActivity().startService(intent)
        }
    }
    private fun pause(){
        binding.btnPause.setOnClickListener{
            val intent = Intent(requireContext(), MusicService::class.java)
            intent.action = MusicService.ACTION_PAUSE
            requireActivity().startService(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicDatabaseHelper.close()
    }



}
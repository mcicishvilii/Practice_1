package com.example.practice_1.fragments

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.IBinder
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.example.practice_1.R
import com.example.practice_1.common.BaseFragment
import com.example.practice_1.data.Song
import com.example.practice_1.databinding.FragmentLessonTwoBinding
import com.example.practice_1.services.MusicService

class LessonTwoFragment :
    BaseFragment<FragmentLessonTwoBinding>(FragmentLessonTwoBinding::inflate) {

    private lateinit var songsUri: Uri
    private lateinit var songsList: MutableList<String>

    @SuppressLint("Range")
    override fun viewCreated() {

        songsUri = Uri.parse("content://com.demo.user.provider/users")

        // Initialize songsList as an empty MutableList
        songsList = mutableListOf()

        val cursor = activity?.contentResolver?.query(songsUri, null, null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndex("name"))
                songsList.add(name)
            }
            it.close()
        }

        binding.tvGenre.text = songsList.size.toString()




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



}
package com.example.practice_1.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.practice_1.common.BaseFragment
import com.example.practice_1.databinding.FragmentLessonTwoBinding
import com.example.practice_1.services.MusicService

class LessonTwoFragment :
    BaseFragment<FragmentLessonTwoBinding>(FragmentLessonTwoBinding::inflate) {


    override fun viewCreated() {

    }

    override fun listeners() {

        play()
        stop()
    }

    private fun play() {
        binding.btnPlay.setOnClickListener {
            Intent(requireContext(), MusicService::class.java).also {
                requireActivity().startService(it)
            }
        }
    }

    private fun stop(){
        binding.btnStop.setOnClickListener{
            Intent(requireContext(), MusicService::class.java).also {
                requireActivity().stopService(it)
            }
        }
    }



}
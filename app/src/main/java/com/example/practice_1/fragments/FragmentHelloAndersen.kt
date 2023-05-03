package com.example.practice_1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.practice_1.R
import com.example.practice_1.common.BaseFragment
import com.example.practice_1.databinding.FragmentHelloAndersenBinding


class FragmentHelloAndersen : BaseFragment<FragmentHelloAndersenBinding>(FragmentHelloAndersenBinding::inflate) {

    override fun viewCreated() {

    }

    override fun listeners() {
        goToNextFragment()
    }

    private fun goToNextFragment(){
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHelloAndersen_to_textWatcherFragment)
        }
    }
}
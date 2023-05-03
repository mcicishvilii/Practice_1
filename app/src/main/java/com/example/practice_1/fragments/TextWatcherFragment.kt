package com.example.practice_1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.practice_1.R
import com.example.practice_1.common.BaseFragment
import com.example.practice_1.databinding.FragmentTextWatcherBinding


class TextWatcherFragment : BaseFragment<FragmentTextWatcherBinding>(FragmentTextWatcherBinding::inflate) {
    override fun viewCreated() {

    }

    override fun listeners() {
        watchText()
        goToNextFragment()
    }

    private fun watchText(){
        var text = ""
        binding.etEditText.doAfterTextChanged {
            text = it.toString()
        }
        binding.btnConfirm.setOnClickListener {
            Toast.makeText(requireContext(),text,Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToNextFragment(){
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_textWatcherFragment_to_listFragment)
        }
    }

}

package com.example.practice_1.fragments.lesson_1_fragments

import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.practice_1.common.BaseFragment
import com.example.practice_1.databinding.FragmentTextWatcherBinding


class TextWatcherFragment : BaseFragment<FragmentTextWatcherBinding>(FragmentTextWatcherBinding::inflate) {
    override fun viewCreated() {

    }

    override fun listeners() {
        watchText()
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
}

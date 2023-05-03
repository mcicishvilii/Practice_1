package com.example.practice_1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_1.R
import com.example.practice_1.adapters.ItemsAdapter
import com.example.practice_1.common.BaseFragment
import com.example.practice_1.data.ItemsPopulate
import com.example.practice_1.databinding.FragmentListBinding


class ListFragment : BaseFragment<FragmentListBinding>(FragmentListBinding::inflate) {

    private val itemsAdapter: ItemsAdapter by lazy { ItemsAdapter() }

    override fun viewCreated() {
        val items = ItemsPopulate.getItems()
        setupRecycler()
        itemsAdapter.submitList(items)
    }

    override fun listeners() {
        notifyItem()
    }


    private fun notifyItem(){
        itemsAdapter.apply {
            setOnItemClickListener { item, _ ->
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog
                    .setMessage("(${item.title}) selected")
                    .setPositiveButton(R.string.okay){_,_->}
                    .create()
                    .show()
            }
        }
    }
    private fun setupRecycler() {
        binding.rvItems.apply {
            adapter = itemsAdapter
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
    }

}
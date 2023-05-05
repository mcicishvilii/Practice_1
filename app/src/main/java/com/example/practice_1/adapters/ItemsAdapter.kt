package com.example.practice_1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_1.adapters.ItemsAdapter.Const.HASIMAGE
import com.example.practice_1.adapters.ItemsAdapter.Const.NOIMAGE
import com.example.practice_1.data.HasImage
import com.example.practice_1.data.Items
import com.example.practice_1.databinding.ItemWithImageBinding
import com.example.practice_1.databinding.ItemWithoutImageBinding

class ItemsAdapter : ListAdapter<Items, RecyclerView.ViewHolder>(ItemsDiffCallback()) {

    private lateinit var itemClickListener: (Items, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HASIMAGE -> WithImageViewHolder(
                ItemWithImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> WithoutImageViewHolder(
                ItemWithoutImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HASIMAGE -> (holder as WithImageViewHolder).bind(getItem(position))
            else -> (holder as WithoutImageViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).hasImage == HasImage.TRUE) HASIMAGE else NOIMAGE
    }

    inner class WithImageViewHolder(private val binding: ItemWithImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Items) {
            binding.apply {
                ivItemImage.setImageResource(item.image!!)
                tvItemWithImageTitle.text = item.title
                tvItemWithImageDesc.text = item.desc

                mainLayout.setOnClickListener {
                    itemClickListener.invoke(item,adapterPosition)
                }
            }
        }
    }

    inner class WithoutImageViewHolder(private val binding: ItemWithoutImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Items) {
            binding.apply {
                tvItemWithoutImageTitle.text = item.title
                tvItemWithoutImageDesc.text = item.desc

                mainLayout.setOnClickListener {
                    itemClickListener.invoke(item,adapterPosition)
                }
            }
        }
    }

    private class ItemsDiffCallback : DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem.desc == newItem.desc
        }

        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem == newItem
        }
    }

    fun setOnItemClickListener(clickListener: (Items, Int) -> Unit) {
        itemClickListener = clickListener
    }

    private object Const {
        const val HASIMAGE = 0 // random unique value
        const val NOIMAGE = 1
    }
}
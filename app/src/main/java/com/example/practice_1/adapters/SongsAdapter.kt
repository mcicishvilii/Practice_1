package com.example.practice_1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_1.data.Song
import com.example.practice_1.databinding.SingleSongLayoutBinding

class SongsAdapter :
    ListAdapter<Song, SongsAdapter.SongsViewHolder>(
        SongsDiffCallBack()
    ) {

    private lateinit var itemClickListener: (Song, Int) -> Unit
    private lateinit var deleteClickListener: (Song, Int) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): SongsViewHolder {
        val binding =
            SingleSongLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.bindData()
    }

    inner class SongsViewHolder(private val binding: SingleSongLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var model: Song? = null

        fun bindData() {
            model = getItem(adapterPosition)
            binding.apply {

                tvSongName.text = model?.songTitle
                tvArtist.text = model?.songArtist
                tvGenre.text = model?.songGenre

            }
            binding.tvSongName.setOnClickListener {
                itemClickListener.invoke(model!!, adapterPosition)
            }

            binding.tvArtist.setOnClickListener {
                deleteClickListener.invoke(model!!,adapterPosition)
            }

        }


    }

    fun setOnItemClickListener(clickListener: (Song, Int) -> Unit) {
        itemClickListener = clickListener
    }

    fun setOnDeleteClickListener(clickListener: (Song, Int) -> Unit) {
        deleteClickListener = clickListener
    }

}

class SongsDiffCallBack :
    DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(
        oldItem: Song,
        newItem: Song
    ): Boolean {
        return oldItem.songTitle == newItem.songTitle
    }

    override fun areContentsTheSame(
        oldItem: Song,
        newItem: Song
    ): Boolean {
        return oldItem == newItem
    }


}
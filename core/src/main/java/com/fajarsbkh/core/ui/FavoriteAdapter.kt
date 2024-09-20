package com.fajarsbkh.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fajarsbkh.core.databinding.ItemListFavoriteBinding
import com.fajarsbkh.core.domain.model.Movie

class FavoriteAdapter(private val onItemClickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(ItemListFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    inner class ListViewHolder(
        private val binding: ItemListFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Movie) {
            binding.apply {
                Glide.with(itemView).load(result.image).into(ivBackdrop)
                tvNameMovie.text = result.title
                tvVote.text = result.voteCount.toString()
                tvYear.text = result.releaseDate
                root.setOnClickListener {
                    onItemClickListener(result)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}
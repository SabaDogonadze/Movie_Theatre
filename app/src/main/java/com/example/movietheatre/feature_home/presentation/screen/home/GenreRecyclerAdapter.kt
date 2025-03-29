package com.example.movietheatre.feature_home.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movietheatre.R
import com.example.movietheatre.databinding.GenreViewholderBinding
import com.example.movietheatre.feature_home.presentation.model.GenresListUi

class GenreRecyclerAdapter :
    ListAdapter<GenresListUi, GenreRecyclerAdapter.GenreItemViewHolder>(object :
        DiffUtil.ItemCallback<GenresListUi>() {
        override fun areItemsTheSame(oldItem: GenresListUi, newItem: GenresListUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GenresListUi,
            newItem: GenresListUi,
        ): Boolean {
            return oldItem == newItem
        }
    }) {
    private var onItemClicked: ((GenresListUi) -> Unit)? = null

    fun setonItemClickedListener(listener: (GenresListUi) -> Unit) {
        onItemClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreItemViewHolder {
        return GenreItemViewHolder(
            GenreViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenreItemViewHolder, position: Int) {
        holder.bind(getItem(position))  //Passes the genre at position to bind() in GenreItemViewHolder.
    }

    inner class GenreItemViewHolder(private val binding: GenreViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GenresListUi) {
            binding.btnGenreTitle.text = item.name
            val backgroundRes = if (item.isSelected) {
                R.drawable.bg_selected_genre_button
            } else {
                R.drawable.bg_genre_button
            }
            binding.btnGenreTitle.background =
                ContextCompat.getDrawable(binding.root.context, backgroundRes)
            binding.btnGenreTitle.setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }
}
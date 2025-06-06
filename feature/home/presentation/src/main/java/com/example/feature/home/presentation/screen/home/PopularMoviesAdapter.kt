package com.example.feature.home.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.core.presentation.extension.loadImg
import com.example.feature.home.presentation.databinding.MoviePopularViewholderBinding
import com.example.feature.home.presentation.model.HomeMovieListUi

class PopularMoviesAdapter(val onClick: (HomeMovieListUi) -> Unit) :
    ListAdapter<HomeMovieListUi, ViewHolder>(object :
        DiffUtil.ItemCallback<HomeMovieListUi>() {
        override fun areItemsTheSame(oldItem: HomeMovieListUi, newItem: HomeMovieListUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HomeMovieListUi,
            newItem: HomeMovieListUi,
        ): Boolean {
            return oldItem == newItem
        }

    }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MovieItemViewHolder(
            MoviePopularViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is MovieItemViewHolder) {
            holder.bind()
        }
    }

    inner class MovieItemViewHolder(private val binding: MoviePopularViewholderBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            val item = currentList[adapterPosition]

            binding.apply {
                tvMovieTitle.text = item.title
                root.setOnClickListener {
                    onClick(item)
                }
                ivMovieImage.loadImg(item.movieImgUrl)
            }
        }
    }
}
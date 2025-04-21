package com.example.movietheatre.feature_home.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.movietheatre.R
import com.example.movietheatre.databinding.MovieViewholderBinding
import com.example.movietheatre.feature_home.presentation.model.HomeMovieListUi

class UpComingMoviesAdapter :
    ListAdapter<HomeMovieListUi, RecyclerView.ViewHolder>(object :
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

    private var onItemClicked: ((HomeMovieListUi) -> Unit)? = null

    fun setonItemClickedListener(listener: (HomeMovieListUi) -> Unit) {
        onItemClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MovieItemViewHolder(
            MovieViewholderBinding.inflate(
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

    inner class MovieItemViewHolder(private val binding: MovieViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = currentList[adapterPosition]

            binding.apply {
                tvMovieTitle.text = item.title
                tvMovieDuration.text = item.duration.toString()
                root.setOnClickListener {
                    onItemClicked?.invoke(item)
                }
            }


            Glide.with(binding.ivMovieImage.context)
                .load(item.movieImgUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.ivMovieImage)

        }
    }
}
package com.example.feature.movie_detail.presentation.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.core.presentation.extension.loadImg
import com.example.core.presentation.extension.toFormattedDateTime
import com.example.feature.movie_detail.presentation.databinding.MovieDetailScreeningViewholderBinding
import com.example.feature.movie_detail.presentation.model.ScreeningPresenter

class MovieDetailScreeningsRecyclerView :
    ListAdapter<ScreeningPresenter, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<ScreeningPresenter>() {
        override fun areItemsTheSame(
            oldItem: ScreeningPresenter,
            newItem: ScreeningPresenter,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ScreeningPresenter,
            newItem: ScreeningPresenter,
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    private var onItemClicked: ((ScreeningPresenter) -> Unit)? = null

    fun setonItemClickedListener(listener: (ScreeningPresenter) -> Unit) {
        onItemClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MovieDetailScreeningViewHolder(
            MovieDetailScreeningViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is MovieDetailScreeningViewHolder) {
            holder.bind()
        }
    }

    inner class MovieDetailScreeningViewHolder(private val binding: MovieDetailScreeningViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = currentList[adapterPosition]

            binding.apply {
                tvMoviePrice.text = item.screeningPrice.toString()
                tvScreeningTime.text = item.screeningTime.toFormattedDateTime()
                root.setOnClickListener {
                    onItemClicked?.invoke(item)
                }

                imgIcon.loadImg(item.iconUrl)
            }
        }
    }
}
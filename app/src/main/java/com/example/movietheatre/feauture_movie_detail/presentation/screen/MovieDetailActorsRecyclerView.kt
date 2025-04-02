package com.example.movietheatre.feauture_movie_detail.presentation.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.movietheatre.R
import com.example.movietheatre.databinding.MovieDetailActorsViewholderBinding
import com.example.movietheatre.feauture_movie_detail.presentation.model.ActorPresenter

class MovieDetailActorsRecyclerView:
    ListAdapter<ActorPresenter, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<ActorPresenter>() {
        override fun areItemsTheSame(oldItem: ActorPresenter, newItem: ActorPresenter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ActorPresenter,
            newItem: ActorPresenter,
        ): Boolean {
            return oldItem == newItem
        }
    }) {

  /*  private var onItemClicked: ((ActorPresenter) -> Unit)? = null

    fun setonItemClickedListener(listener: (ActorPresenter) -> Unit) {
        onItemClicked = listener
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MovieDetailActorsViewHolder(
            MovieDetailActorsViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is MovieDetailActorsViewHolder) {
            holder.bind()
        }
    }

    inner class MovieDetailActorsViewHolder(private val binding: MovieDetailActorsViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = currentList[adapterPosition]

            binding.apply {
                tvActorName.text = item.name
            }


            Glide.with(binding.ivActor.context)
                .load(item.actorImgUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.ivActor)

        }
    }
}
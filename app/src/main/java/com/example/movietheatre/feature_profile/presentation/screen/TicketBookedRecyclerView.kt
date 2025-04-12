package com.example.movietheatre.feature_profile.presentation.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.movietheatre.R
import com.example.movietheatre.databinding.BookedTicketViewholderBinding
import com.example.movietheatre.feature_profile.presentation.model.TicketPresenter
import com.example.movietheatre.feauture_movie_detail.presentation.extension.toFormattedDateTime

class TicketBookedRecyclerView : ListAdapter<TicketPresenter, ViewHolder>(object :
    DiffUtil.ItemCallback<TicketPresenter>() {
    override fun areItemsTheSame(oldItem: TicketPresenter, newItem: TicketPresenter): Boolean {
        return oldItem.bookingId == newItem.bookingId
    }

    override fun areContentsTheSame(
        oldItem: TicketPresenter,
        newItem: TicketPresenter,
    ): Boolean {
        return oldItem == newItem
    }

}) {

    private var onItemClicked: ((TicketPresenter) -> Unit)? = null

    fun setonItemClickedListener(listener: (TicketPresenter) -> Unit) {
        onItemClicked = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return TicketBookedViewHolder(
            BookedTicketViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is TicketBookedViewHolder) {
            holder.bind()
        }
    }

    inner class TicketBookedViewHolder(private val binding: BookedTicketViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = currentList[adapterPosition]

            binding.apply {
                tvMovieTitle.text = item.movieTitle
                tvScreeningTime.text = item.screeningTime
                tvScreeningSeats.text = item.seatNumbers.toString()
                tvScreeningPrice.text = item.totalMoney.toString()
                tvScreeningTime.text = item.screeningTime.toFormattedDateTime()
                root.setOnClickListener {
                    onItemClicked?.invoke(item)
                }
            }

            Glide.with(binding.root)
                .load(item.movieImgUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.ivMovieImage)
        }
    }
}
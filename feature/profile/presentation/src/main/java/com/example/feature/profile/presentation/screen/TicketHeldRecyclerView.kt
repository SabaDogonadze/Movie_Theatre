package com.example.feature.profile.presentation.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.core.presentation.extension.loadImg
import com.example.core.presentation.extension.toFormattedDateTime
import com.example.feature.profile.presentation.databinding.HeldTicketViewholderBinding
import com.example.feature.profile.presentation.model.TicketPresenter

class TicketHeldRecyclerView :
    ListAdapter<TicketPresenter, ViewHolder>(object :
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
            HeldTicketViewholderBinding.inflate(
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

    inner class TicketBookedViewHolder(private val binding: HeldTicketViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = getItem(adapterPosition)

            binding.apply {
                tvMovieTitle.text = item.movieTitle
                tvScreeningTime.text = item.screeningTime
                tvScreeningSeats.text = item.seatNumbers
                tvScreeningPrice.text = item.totalMoney.toString()
                tvScreeningTime.text = item.screeningTime.toFormattedDateTime()
                root.setOnClickListener {
                    onItemClicked?.invoke(item)
                }
                ivMovieImage.loadImg(item.movieImgUrl)
            }
        }
    }
}
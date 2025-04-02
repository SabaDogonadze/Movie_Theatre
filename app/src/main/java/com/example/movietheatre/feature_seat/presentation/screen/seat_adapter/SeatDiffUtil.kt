package com.example.movietheatre.feature_seat.presentation.screen.seat_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.movietheatre.feature_seat.presentation.model.Seat

object SeatDiffUtil : DiffUtil.ItemCallback<Seat>() {
    override fun areItemsTheSame(oldItem: Seat, newItem: Seat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Seat, newItem: Seat): Boolean {
        return oldItem == newItem
    }
}
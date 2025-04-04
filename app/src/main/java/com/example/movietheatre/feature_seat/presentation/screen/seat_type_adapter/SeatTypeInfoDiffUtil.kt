package com.example.movietheatre.feature_seat.presentation.screen.seat_type_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.movietheatre.feature_seat.presentation.model.SeatTypeInfo

object SeatTypeInfoDiffUtil : DiffUtil.ItemCallback<SeatTypeInfo>() {
    override fun areItemsTheSame(oldItem: SeatTypeInfo, newItem: SeatTypeInfo): Boolean {
        return oldItem.seatType.ordinal == newItem.seatType.ordinal
    }

    override fun areContentsTheSame(oldItem: SeatTypeInfo, newItem: SeatTypeInfo): Boolean {
        return oldItem.seatType == newItem.seatType
    }
}
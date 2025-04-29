package com.example.feature.seat.presentation.screen.seat.seat_row_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.feature.seat.presentation.model.SeatRow

object SeatRowDiffUtil : DiffUtil.ItemCallback<SeatRow>() {
    override fun areItemsTheSame(oldItem: SeatRow, newItem: SeatRow): Boolean {
        return oldItem.rowId == newItem.rowId
    }

    override fun areContentsTheSame(oldItem: SeatRow, newItem: SeatRow): Boolean {
        return oldItem == newItem
    }
}
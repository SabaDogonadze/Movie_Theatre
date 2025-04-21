package com.example.movietheatre.feature_seat.presentation.screen.seat.seat_row_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movietheatre.databinding.ViewholderSeatRowBinding
import com.example.movietheatre.feature_seat.presentation.model.Seat
import com.example.movietheatre.feature_seat.presentation.model.SeatRow
import com.example.movietheatre.feature_seat.presentation.screen.seat.seat_adapter.SeatAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class SeatRowAdapter(val onSeatClicked: (Seat) -> Unit) :
    ListAdapter<SeatRow, SeatRowAdapter.SeatRowViewHolder>(SeatRowDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatRowViewHolder {
        val binding =
            ViewholderSeatRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeatRowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeatRowViewHolder, position: Int) {
        holder.onBind()
    }

    inner class SeatRowViewHolder(val binding: ViewholderSeatRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val seatRow = getItem(adapterPosition)

            binding.txtSeatRowIdentifier.text = seatRow.rowId

            val seatAdapter = SeatAdapter(onSeatClicked)

            binding.rvSeat.apply {
                adapter = seatAdapter
                layoutManager = FlexboxLayoutManager(binding.root.context).apply {
                    flexDirection = FlexDirection.ROW
                    justifyContent = JustifyContent.SPACE_BETWEEN
                }
            }

            seatAdapter.submitList(seatRow.seats.toList())
        }
    }

}
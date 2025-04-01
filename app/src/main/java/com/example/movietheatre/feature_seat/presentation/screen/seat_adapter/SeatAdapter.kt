package com.example.movietheatre.feature_seat.presentation.screen.seat_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movietheatre.core.presentation.extension.setTint
import com.example.movietheatre.databinding.ViewholderSeatBinding
import com.example.movietheatre.feature_seat.presentation.model.Seat
import com.example.movietheatre.feature_seat.presentation.util.SeatType

class SeatAdapter(val onClick: (Seat) -> Unit) :
    ListAdapter<Seat, SeatAdapter.SeatViewHolder>(SeatDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val binding = ViewholderSeatBinding.inflate(LayoutInflater.from(parent.context))
        return SeatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.onBind()
    }

    inner class SeatViewHolder(val binding: ViewholderSeatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val seat = getItem(adapterPosition)

            when (seat.status) {
                SeatType.BOOKED -> binding.imgSeat.setTint(seat.status.colorId)
                SeatType.HELD -> binding.imgSeat.setTint(seat.status.colorId)
                SeatType.FREE -> binding.imgSeat.setTint(seat.status.colorId)
                SeatType.SELECTED -> binding.imgSeat.setTint(seat.status.colorId)

            }

            binding.root.setOnClickListener {
                if (seat.status == SeatType.FREE) {
                    onClick(seat)
                }
            }
        }
    }

}
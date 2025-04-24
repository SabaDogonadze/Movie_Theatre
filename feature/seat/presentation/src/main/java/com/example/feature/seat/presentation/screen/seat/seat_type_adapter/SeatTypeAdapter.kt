package com.example.feature.seat.presentation.screen.seat.seat_type_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.presentation.extension.setTint
import com.example.feature.seat.presentation.databinding.ViewholderSeatTypeBinding
import com.example.feature.seat.presentation.model.SeatTypeInfo

class SeatTypeAdapter :
    ListAdapter<SeatTypeInfo, SeatTypeAdapter.SeatTypeViewHolder>(SeatTypeInfoDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatTypeViewHolder {
        val binding =
            ViewholderSeatTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeatTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeatTypeViewHolder, position: Int) {
        holder.onBind()
    }


    inner class SeatTypeViewHolder(val binding: ViewholderSeatTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val seatTypeInfo = getItem(adapterPosition)

            binding.imgSeatTypeColor.setTint(seatTypeInfo.seatType.colorId)
            binding.txtSeatType.text = seatTypeInfo.seatType.name
        }
    }

}
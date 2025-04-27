package com.example.feature.seat.presentation.util

import com.example.core.presentation.R

enum class SeatType(val colorId: Int) {
    BOOKED(R.color.purple), HELD(R.color.orange), FREE(R.color.cyan), SELECTED(R.color.white)
}
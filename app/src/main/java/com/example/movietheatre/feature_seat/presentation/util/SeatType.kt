package com.example.movietheatre.feature_seat.presentation.util

import com.example.movietheatre.R

enum class SeatType(val colorId: Int) {
    BOOKED(R.color.purple), HELD(R.color.orange), FREE(R.color.cyan), SELECTED(R.color.white)
}
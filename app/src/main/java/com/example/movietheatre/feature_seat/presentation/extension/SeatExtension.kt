package com.example.movietheatre.feature_seat.presentation.extension

import com.example.movietheatre.feature_seat.presentation.model.Seat

fun Seat.asString(): String {
    return if (this.vipAddOn > 0) this.seatNumber + "(VIP)" else this.seatNumber
}
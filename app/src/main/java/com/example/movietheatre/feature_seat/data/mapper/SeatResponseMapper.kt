package com.example.movietheatre.feature_seat.data.mapper

import com.example.movietheatre.feature_seat.data.remote.response.SeatResponse
import com.example.movietheatre.feature_seat.domain.model.GetSeat

fun SeatResponse.toDomain(): GetSeat = GetSeat(
    id = id, seatNumber = seatNumber, status = status, vipAddOn = vipAddOn
)
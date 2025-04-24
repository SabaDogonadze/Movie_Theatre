package com.example.feature.seat.data.mapper

import com.example.feature.seat.data.remote.response.SeatResponse
import com.example.feature.seat.domain.model.GetSeat

fun SeatResponse.toDomain(): GetSeat = GetSeat(
    id = id, seatNumber = seatNumber, status = status, vipAddOn = vipAddOn
)
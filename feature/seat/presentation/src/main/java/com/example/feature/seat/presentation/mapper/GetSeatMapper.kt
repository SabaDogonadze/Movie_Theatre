package com.example.feature.seat.presentation.mapper

import com.example.feature.seat.domain.model.GetSeat
import com.example.feature.seat.presentation.model.Seat
import com.example.feature.seat.presentation.model.SeatRow
import com.example.feature.seat.presentation.util.SeatType

fun GetSeat.toPresentation(): Seat = Seat(
    id = id, seatNumber = seatNumber,
    status = when (this.status.lowercase()) {
        "booked" -> SeatType.BOOKED
        "held" -> SeatType.HELD
        else -> SeatType.FREE
    }, vipAddOn = vipAddOn, imgUrl = imgUrl
)


private fun groupSeatsByRow(seats: List<Seat>): Map<String, List<Seat>> {
    return seats.groupBy { seat ->
        // Extract the row identifier (first character) from the seat number
        seat.seatNumber.first().toString()
    }
}

fun List<Seat>.createRowModels(): List<SeatRow> {
    val groupedSeats = groupSeatsByRow(this)

    return groupedSeats.entries
        .sortedBy { it.key }
        .map { (rowId, rowSeats) ->
            val sortedRowSeats = rowSeats.sortedBy {
                val numberPart = it.seatNumber.substring(1).toIntOrNull() ?: 0
                numberPart
            }
            SeatRow(rowId, sortedRowSeats)
        }
}

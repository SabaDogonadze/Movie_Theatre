package com.example.movietheatre.feature_seat.presentation.mapper

import com.example.movietheatre.feature_seat.domain.model.GetSeat
import com.example.movietheatre.feature_seat.presentation.model.Seat
import com.example.movietheatre.feature_seat.presentation.model.SeatRow
import com.example.movietheatre.feature_seat.presentation.util.SeatType

fun GetSeat.toPresentation(): Seat = Seat(
    id = id, seatNumber = seatNumber,
    status = when (this.status.lowercase()) {
        "booked" -> SeatType.BOOKED
        "held" -> SeatType.HELD
        else -> SeatType.FREE
    }, vipAddOn = vipAddOn
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
            // Sort seats within each row by their numerical position (A1, A2, A3, etc.)
            val sortedRowSeats = rowSeats.sortedBy {
                // Extract numeric part from seat number (e.g., "1" from "A1")
                val numberPart = it.seatNumber.substring(1).toIntOrNull() ?: 0
                numberPart
            }
            SeatRow(rowId, sortedRowSeats)
        }
}

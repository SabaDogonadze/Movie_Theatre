package com.example.movietheatre.core.presentation.extension

import com.example.movietheatre.core.presentation.util.TicketStatus

fun TicketStatus.toDomain(): String {
    return when (this) {
        TicketStatus.BOOKED -> "BOOKED"
        TicketStatus.FREE -> "FREE"
        TicketStatus.HELD -> "HELD"
    }
}
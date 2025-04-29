package com.example.feature.profile.data.remote.mapper

import com.example.feature.profile.data.remote.model.TicketDto
import com.example.feature.profile.data.remote.model.UserTicketsDto
import com.example.feature.profile.domain.model.Ticket
import com.example.feature.profile.domain.model.UserTickets

fun UserTicketsDto.toDomain(): UserTickets {
    return UserTickets(tickets = tickets.map { it.toDomain() })
}

fun TicketDto.toDomain(): Ticket {
    return Ticket(
        bookingId = bookingId,
        movieTitle = movieTitle,
        movieImgUrl = movieImgUrl,
        screeningId = screeningId,
        screeningTime = screeningTime,
        seatNumbers = seatNumbers,
        seatType = seatType,
        totalMoney = totalMoney
    )
}
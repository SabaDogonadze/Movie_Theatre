package com.example.feature.profile.presentation.mapper

import com.example.feature.profile.domain.model.Ticket
import com.example.feature.profile.domain.model.UserTickets
import com.example.feature.profile.presentation.model.TicketPresenter
import com.example.feature.profile.presentation.model.UserTicketsPresenter

fun UserTickets.toPresenter(): UserTicketsPresenter {
    return UserTicketsPresenter(tickets = tickets.map { it.toPresenter() })
}

fun Ticket.toPresenter(): TicketPresenter {
    return TicketPresenter(
        bookingId = bookingId,
        movieTitle = movieTitle,
        movieImgUrl = movieImgUrl,
        screeningTime = screeningTime,
        seatNumbers = seatNumbers,
        seatType = seatType,
        screeningId = screeningId,
        totalMoney = totalMoney
    )
}
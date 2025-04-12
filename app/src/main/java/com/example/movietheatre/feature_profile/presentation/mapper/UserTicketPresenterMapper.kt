package com.example.movietheatre.feature_profile.presentation.mapper

import com.example.movietheatre.feature_profile.domain.model.Ticket
import com.example.movietheatre.feature_profile.domain.model.UserTickets
import com.example.movietheatre.feature_profile.presentation.model.TicketPresenter
import com.example.movietheatre.feature_profile.presentation.model.UserTicketsPresenter

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
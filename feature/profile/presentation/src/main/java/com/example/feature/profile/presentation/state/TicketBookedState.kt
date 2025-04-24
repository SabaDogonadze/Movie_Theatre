package com.example.feature.profile.presentation.state

import com.example.feature.profile.presentation.model.UserTicketsPresenter

data class TicketBookedState(
    val isLoading: Boolean = false,
    val isTicketDeleted: Boolean = false,
    val userTickets: UserTicketsPresenter = UserTicketsPresenter(tickets = listOf()),
)
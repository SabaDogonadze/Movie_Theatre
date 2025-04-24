package com.example.feature.profile.presentation.state

import com.example.feature.profile.presentation.model.UserTicketsPresenter

data class TicketHeldState(
    val isLoading: Boolean = false,
    val isTicketDeleted: Boolean = false,
    val userTickets: UserTicketsPresenter = UserTicketsPresenter(tickets = listOf()),
    val isTicketUpdated: Boolean = false,
)
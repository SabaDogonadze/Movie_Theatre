package com.example.feature.profile.presentation.mapper

import com.example.feature.profile.domain.model.DeleteUsersTicket
import com.example.feature.profile.presentation.model.DeleteUsersTicketPresenter

fun DeleteUsersTicket.toPresenter(): DeleteUsersTicketPresenter {
    return DeleteUsersTicketPresenter(deletedTicketStatus = deletedTicketStatus)
}
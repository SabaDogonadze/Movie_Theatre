package com.example.movietheatre.feature_profile.presentation.mapper

import com.example.movietheatre.feature_profile.domain.model.DeleteUsersTicket
import com.example.movietheatre.feature_profile.presentation.model.DeleteUsersTicketPresenter

fun DeleteUsersTicket.toPresenter():DeleteUsersTicketPresenter{
    return DeleteUsersTicketPresenter(deletedTicketStatus = deletedTicketStatus)
}
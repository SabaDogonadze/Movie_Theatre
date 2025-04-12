package com.example.movietheatre.feature_profile.data.remote.mapper

import com.example.movietheatre.feature_profile.data.remote.model.DeleteUsersTicketDto
import com.example.movietheatre.feature_profile.domain.model.DeleteUsersTicket

fun DeleteUsersTicketDto.toDomain():DeleteUsersTicket{
    return DeleteUsersTicket(deletedTicketStatus = deletedTicketStatus)
}
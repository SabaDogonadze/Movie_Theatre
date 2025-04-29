package com.example.feature.profile.data.remote.mapper

import com.example.feature.profile.data.remote.model.DeleteUsersTicketDto
import com.example.feature.profile.domain.model.DeleteUsersTicket

fun DeleteUsersTicketDto.toDomain(): DeleteUsersTicket {
    return DeleteUsersTicket(deletedTicketStatus = deletedTicketStatus)
}
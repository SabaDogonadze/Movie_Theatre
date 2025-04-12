package com.example.movietheatre.feature_profile.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteUsersTicketDto(
    @SerialName("deleted") val deletedTicketStatus: Boolean
)

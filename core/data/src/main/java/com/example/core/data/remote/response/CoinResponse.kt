package com.example.core.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CoinResponse(
    val userUid: String,
    val coins: Int,
)
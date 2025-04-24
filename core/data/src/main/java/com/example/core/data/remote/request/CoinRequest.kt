package com.example.core.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
class CoinRequest(
    val amount: Int,
)
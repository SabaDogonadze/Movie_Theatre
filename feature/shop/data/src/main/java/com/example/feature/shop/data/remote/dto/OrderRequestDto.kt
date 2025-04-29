package com.example.feature.shop.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val userUid: String,
    val items: List<OrderItemDto>,
)

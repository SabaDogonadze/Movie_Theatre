package com.example.movietheatre.feature_shop.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class OrderDto(
    val trackingCode: Int,
)
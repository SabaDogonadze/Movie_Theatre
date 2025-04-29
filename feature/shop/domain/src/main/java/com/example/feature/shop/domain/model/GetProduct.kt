package com.example.feature.shop.domain.model

data class GetProduct(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String,
)

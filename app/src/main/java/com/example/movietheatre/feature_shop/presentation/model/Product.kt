package com.example.movietheatre.feature_shop.presentation.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String,
    val quantity: Int = 0,
)

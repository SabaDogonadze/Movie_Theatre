package com.example.movietheatre.feature_shop.presentation.model

data class CategoryProducts(
    val id: Int,
    val category: String,
    val products: List<Product>,
)
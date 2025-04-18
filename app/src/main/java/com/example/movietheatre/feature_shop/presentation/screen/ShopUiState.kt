package com.example.movietheatre.feature_shop.presentation.screen

import com.example.movietheatre.feature_shop.presentation.model.CategoryProducts
import com.example.movietheatre.feature_shop.presentation.model.Product

data class ShopUiState(
    val isLoading: Boolean = false,
    val products: List<CategoryProducts> = listOf(),
    val selectedProduct: List<Product> = listOf(),
)
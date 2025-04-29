package com.example.feature.shop.presentation.screen

import com.example.feature.shop.presentation.model.CategoryProducts
import com.example.feature.shop.presentation.model.Product

data class ShopUiState(
    val isLoading: Boolean = false,
    val products: List<CategoryProducts> = listOf(),
    val selectedProduct: List<Product> = listOf(),
    val userCoins: Int = 0,
)
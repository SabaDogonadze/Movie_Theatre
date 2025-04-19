package com.example.movietheatre.feature_profile.presentation.my_shop

import com.example.movietheatre.feature_profile.presentation.model.UserOrder

data class MyShopUiState(
    val isLoading: Boolean = false,
    val products: List<UserOrder> = listOf(),
)
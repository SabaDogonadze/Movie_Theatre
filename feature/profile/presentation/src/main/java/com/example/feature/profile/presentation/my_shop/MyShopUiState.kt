package com.example.feature.profile.presentation.my_shop

import com.example.feature.profile.presentation.model.UserOrder


data class MyShopUiState(
    val isLoading: Boolean = false,
    val products: List<UserOrder> = listOf(),
)
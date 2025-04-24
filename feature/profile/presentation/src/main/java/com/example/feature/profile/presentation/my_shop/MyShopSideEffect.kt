package com.example.feature.profile.presentation.my_shop

sealed interface MyShopSideEffect {
    data class ShowError(val message: Int) : MyShopSideEffect
}
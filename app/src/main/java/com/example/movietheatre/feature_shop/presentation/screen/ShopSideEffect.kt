package com.example.movietheatre.feature_shop.presentation.screen

sealed interface ShopSideEffect {

    data class ShowError(val message: Int) : ShopSideEffect
    data class SuccessfulOrder(val trackingCode: Int) : ShopSideEffect

}
package com.example.movietheatre.feature_profile.presentation.my_shop

sealed interface MyShopEvent {

    data object GetUserOrder:MyShopEvent
}
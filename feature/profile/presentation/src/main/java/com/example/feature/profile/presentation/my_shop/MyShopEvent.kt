package com.example.feature.profile.presentation.my_shop

sealed interface MyShopEvent {

    data object GetUserOrder:MyShopEvent
}
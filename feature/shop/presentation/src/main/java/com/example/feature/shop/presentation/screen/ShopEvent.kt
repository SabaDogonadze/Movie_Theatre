package com.example.feature.shop.presentation.screen

import com.example.feature.shop.presentation.model.Product

sealed interface ShopEvent {

    object GetProducts : ShopEvent
    object GetCoin : ShopEvent

    object BuyWithCoin : ShopEvent

    object RefreshLayout : ShopEvent

    data class AddProduct(val product: Product) : ShopEvent
    data class RemoveProduct(val product: Product) : ShopEvent

    data object Order : ShopEvent
}
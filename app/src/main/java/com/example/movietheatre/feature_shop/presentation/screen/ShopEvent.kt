package com.example.movietheatre.feature_shop.presentation.screen

import com.example.movietheatre.feature_shop.presentation.model.Product

sealed interface ShopEvent {

    object GetProducts : ShopEvent

    data class AddProduct(val product: Product) : ShopEvent
    data class RemoveProduct(val product: Product) : ShopEvent

}
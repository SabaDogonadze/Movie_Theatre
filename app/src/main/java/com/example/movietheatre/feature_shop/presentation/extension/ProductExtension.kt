package com.example.movietheatre.feature_shop.presentation.extension

import com.example.movietheatre.core.presentation.extension.asMoneyFormat
import com.example.movietheatre.feature_shop.presentation.model.Product

fun List<Product>.formatSelectedProducts(): String {
    if (this.isEmpty()) return ""

    return this.joinToString(", ") { product ->
        if (product.quantity > 1) {
            "${product.name} (${product.quantity}x)"
        } else {
            product.name
        }
    }
}

fun List<Product>.calculateTotalPrice(): String {
    return this.sumOf { product -> product.price * product.quantity }.toFloat().asMoneyFormat()
}
package com.example.feature.shop.presentation.mapper

import com.example.feature.shop.domain.model.GetProduct
import com.example.feature.shop.presentation.model.CategoryProducts
import com.example.feature.shop.presentation.model.Product

fun GetProduct.toPresentation(): Product = Product(
    id = this.id,
    name = this.name,
    category = this.category,
    price = this.price,
    imageUrl = this.imageUrl,
    description = this.description
)


fun List<GetProduct>.groupByCategory(): List<CategoryProducts> =
    this
        .groupBy { it.category }
        .entries
        .mapIndexed { index, (category, items) ->
            CategoryProducts(
                id = index,
                category = category,
                products = items.map { it.toPresentation() }
            )
        }
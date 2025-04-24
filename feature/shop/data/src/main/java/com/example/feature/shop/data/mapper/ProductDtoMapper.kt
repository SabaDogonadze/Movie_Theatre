package com.example.feature.shop.data.mapper

import com.example.feature.shop.data.remote.dto.ProductDto
import com.example.feature.shop.domain.model.GetProduct


fun ProductDto.toDomain(): GetProduct = GetProduct(
    id = this.id,
    name = this.name,
    category = this.category,
    price = this.price,
    imageUrl = this.imageUrl,
    description = this.description
)
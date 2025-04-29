package com.example.feature.shop.domain.repository

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.shop.domain.model.GetProduct

interface ProductRepository {
    suspend fun getProducts(): Resource<List<GetProduct>, NetworkError>
}
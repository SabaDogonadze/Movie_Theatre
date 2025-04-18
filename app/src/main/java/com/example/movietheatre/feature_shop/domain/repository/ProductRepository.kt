package com.example.movietheatre.feature_shop.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_shop.domain.model.GetProduct

interface ProductRepository{
    suspend fun getProducts():Resource<List<GetProduct>,NetworkError>
}
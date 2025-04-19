package com.example.movietheatre.feature_shop.data.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_shop.data.mapper.toDomain
import com.example.movietheatre.feature_shop.data.remote.service.ProductService
import com.example.movietheatre.feature_shop.domain.model.GetProduct
import com.example.movietheatre.feature_shop.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val productService: ProductService,
) : ProductRepository {
    override suspend fun getProducts(): Resource<List<GetProduct>, NetworkError> {
        return apiHelper.handleHttpRequest {
            productService.getProducts()
        }.mapData { it.map { it.toDomain() } }
    }
}
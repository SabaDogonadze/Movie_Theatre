package com.example.feature.shop.data.repository

import com.example.core.data.common.ApiHelper
import com.example.core.domain.extension.mapData
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.shop.data.mapper.toDomain
import com.example.feature.shop.data.remote.service.ProductService
import com.example.feature.shop.domain.model.GetProduct
import com.example.feature.shop.domain.repository.ProductRepository
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
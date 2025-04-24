package com.example.feature.shop.domain.use_case

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.shop.domain.model.GetProduct
import com.example.feature.shop.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {

    suspend operator fun invoke(): Resource<List<GetProduct>, NetworkError> {
        return productRepository.getProducts()
    }
}
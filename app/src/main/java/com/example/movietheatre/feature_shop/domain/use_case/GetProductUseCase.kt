package com.example.movietheatre.feature_shop.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_shop.domain.model.GetProduct
import com.example.movietheatre.feature_shop.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {

    suspend operator fun invoke(): Resource<List<GetProduct>, NetworkError> {
        return productRepository.getProducts()
    }
}
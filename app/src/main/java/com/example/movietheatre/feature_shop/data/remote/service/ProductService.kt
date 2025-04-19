package com.example.movietheatre.feature_shop.data.remote.service

import com.example.movietheatre.feature_shop.data.remote.dto.OrderRequest
import com.example.movietheatre.feature_shop.data.remote.dto.ProductDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductService {
    @GET("concessions/products")
    suspend fun getProducts(): Response<List<ProductDto>>

}
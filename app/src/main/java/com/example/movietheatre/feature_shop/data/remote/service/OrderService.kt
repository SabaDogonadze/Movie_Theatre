package com.example.movietheatre.feature_shop.data.remote.service

import com.example.movietheatre.feature_shop.data.remote.dto.OrderDto
import com.example.movietheatre.feature_shop.data.remote.dto.OrderRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderService {

    @POST("concessions/orders")
    suspend fun createOrder(@Body orderRequest: OrderRequest): Response<OrderDto>
}
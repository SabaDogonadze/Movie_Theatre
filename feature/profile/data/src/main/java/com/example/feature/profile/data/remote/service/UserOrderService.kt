package com.example.feature.profile.data.remote.service

import com.example.feature.profile.data.remote.model.UserOrderDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserOrderService {
    @GET("concessions/orders/user/{userUid}")
    suspend fun getUserOrder(@Path("userUid") userUid: String): Response<List<UserOrderDto>>
}
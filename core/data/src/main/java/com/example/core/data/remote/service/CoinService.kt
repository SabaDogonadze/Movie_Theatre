package com.example.core.data.remote.service

import com.example.core.data.remote.request.CoinRequest
import com.example.core.data.remote.response.CoinResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoinService {

    @GET("coins/{userUid}")
    suspend fun getCoins(@Path("userUid") userUid: String): Response<CoinResponse>

    @POST("coins/{userUid}")
    suspend fun updateCoins(
        @Path("userUid") userUid: String,
        @Body request: CoinRequest,
    ): Response<CoinResponse>

}
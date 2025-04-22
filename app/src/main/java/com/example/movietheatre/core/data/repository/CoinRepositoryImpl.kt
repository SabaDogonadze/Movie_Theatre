package com.example.movietheatre.core.data.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.data.mapper.toDomain
import com.example.movietheatre.core.data.remote.request.CoinRequest
import com.example.movietheatre.core.data.remote.service.CoinService
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.model.GetCoin
import com.example.movietheatre.core.domain.repository.CoinRepository
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val coinService: CoinService,
    private val firebaseAuth: FirebaseAuth,
) : CoinRepository {
    override suspend fun getCoins(): Resource<GetCoin, NetworkError> {
        return apiHelper.handleHttpRequest {
            coinService.getCoins(firebaseAuth.currentUser!!.uid)
        }.mapData { it.toDomain() }
    }

    override suspend fun updateCoins(amount: Int): Resource<GetCoin, NetworkError> {
        return apiHelper.handleHttpRequest {
            coinService.updateCoins(firebaseAuth.currentUser!!.uid,CoinRequest(amount = -amount))
        }.mapData { it.toDomain() }
    }
}
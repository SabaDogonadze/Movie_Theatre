package com.example.movietheatre.core.domain.repository

import com.example.movietheatre.core.domain.model.GetCoin
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError

interface CoinRepository {
    suspend fun getCoins(): Resource<GetCoin, NetworkError>

    suspend fun updateCoins(amount: Int): Resource<GetCoin, NetworkError>
}
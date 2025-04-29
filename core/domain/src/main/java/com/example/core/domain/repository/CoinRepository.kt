package com.example.core.domain.repository

import com.example.core.domain.model.GetCoin
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError

interface CoinRepository {
    suspend fun getCoins(): Resource<GetCoin, NetworkError>

    suspend fun updateCoins(amount: Int): Resource<GetCoin, NetworkError>
}
package com.example.core.domain.use_case

import com.example.core.domain.model.GetCoin
import com.example.core.domain.repository.CoinRepository
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val coinRepository: CoinRepository,
) {
    suspend operator fun invoke(): Resource<GetCoin, NetworkError> {
        return coinRepository.getCoins()
    }
}
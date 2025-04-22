package com.example.movietheatre.core.domain.use_case

import com.example.movietheatre.core.domain.model.GetCoin
import com.example.movietheatre.core.domain.repository.CoinRepository
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import javax.inject.Inject

class UpdateCoinUseCase @Inject constructor(
    private val coinRepository: CoinRepository,
) {
    suspend operator fun invoke(amount: Int): Resource<GetCoin, NetworkError> {
        return coinRepository.updateCoins(amount)
    }
}
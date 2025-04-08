package com.example.movietheatre.feature_payment.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_payment.domain.manager.CardManager
import com.example.movietheatre.feature_payment.domain.model.GetCard
import com.example.movietheatre.feature_payment.domain.util.DatastoreError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val cardManager: CardManager) {
    suspend operator fun invoke(): Flow<Resource<List<GetCard>, DatastoreError>> {
        return cardManager.getCards()
    }
}
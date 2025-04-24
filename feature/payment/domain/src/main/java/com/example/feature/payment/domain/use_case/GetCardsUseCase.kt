package com.example.feature.payment.domain.use_case

import com.example.core.domain.util.Resource
import com.example.feature.payment.domain.manager.CardManager
import com.example.feature.payment.domain.model.GetCard
import com.example.feature.payment.domain.util.DatastoreError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val cardManager: CardManager) {
    suspend operator fun invoke(): Flow<Resource<List<GetCard>, DatastoreError>> {
        return cardManager.getCards()
    }
}
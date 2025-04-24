package com.example.feature.payment.domain.use_case

import com.example.core.domain.util.Resource
import com.example.feature.payment.domain.manager.CardManager
import com.example.feature.payment.domain.util.DatastoreError
import javax.inject.Inject


class DeleteCardUseCase @Inject constructor(private val cardManager: CardManager) {
    suspend operator fun invoke(cardNumber: String): Resource<Unit, DatastoreError> {
        return cardManager.deleteCard(cardNumber)
    }
}
package com.example.feature.payment.domain.use_case

import com.example.core.domain.util.Resource
import com.example.feature.payment.domain.manager.CardManager
import com.example.feature.payment.domain.model.GetCard
import com.example.feature.payment.domain.util.AddCardError
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val cardManager: CardManager) {
    suspend operator fun invoke(card: GetCard): Resource<Unit, AddCardError> {
        return cardManager.addCard(card)
    }
}
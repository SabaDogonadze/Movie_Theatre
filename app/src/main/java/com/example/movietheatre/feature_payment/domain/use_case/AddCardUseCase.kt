package com.example.movietheatre.feature_payment.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_payment.domain.manager.CardManager
import com.example.movietheatre.feature_payment.domain.model.GetCard
import com.example.movietheatre.feature_payment.domain.util.AddCardError
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val cardManager: CardManager) {
    suspend operator fun invoke(card: GetCard): Resource<Unit, AddCardError> {
        return cardManager.addCard(card)
    }
}
package com.example.movietheatre.feature_payment.presentation.model

import com.example.movietheatre.feature_payment.domain.model.CardType

data class Card(
    val cardNumber: String,
    val cardHolderName: String,
    val expiryDate: String,
    val cvv: String,
    val cardType: CardType,
) {
    val maskedNumber = "**** **** **** ${cardNumber.takeLast(4)}"
}
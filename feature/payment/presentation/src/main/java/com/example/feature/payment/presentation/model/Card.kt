package com.example.feature.payment.presentation.model

import com.example.feature.payment.domain.model.CardType

data class Card(
    val cardNumber: String,
    val cardHolderName: String,
    val expiryDate: String,
    val cvv: String,
    val cardType: com.example.feature.payment.domain.model.CardType,
) {
    val maskedNumber = "**** **** **** ${cardNumber.takeLast(4)}"
}
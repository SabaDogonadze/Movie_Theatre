package com.example.feature.payment.domain.model

data class GetCard(
    val cardNumber: String,
    val cardHolderName: String,
    val expiryDate: String,
    val cvv: String,
    val cardType: CardType
)

enum class CardType {
    MASTERCARD, VISA
}
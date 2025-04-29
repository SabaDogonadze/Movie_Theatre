package com.example.feature.payment.presentation.mapper

import com.example.feature.payment.domain.model.GetCard
import com.example.feature.payment.presentation.model.Card


fun GetCard.toPresentation(): Card {
    return Card(
        cardHolderName = cardHolderName,
        cardNumber = cardNumber,
        cardType = cardType,
        cvv = cvv,
        expiryDate = expiryDate
    )
}

fun Card.toDomain(): GetCard {
    return GetCard(
        cardHolderName = cardHolderName,
        cardNumber = cardNumber,
        cardType = cardType,
        cvv = cvv,
        expiryDate = expiryDate
    )
}


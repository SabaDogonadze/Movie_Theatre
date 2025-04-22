package com.example.movietheatre.feature_payment.presentation.screen.payment

import com.example.movietheatre.feature_payment.presentation.model.Card

data class PaymentUiState(
    val isLoading: Boolean = false,
    val cards: List<Card> = emptyList(),
    val userCoins: Int = 0,
    val selectedCoins: Int = 0,
)
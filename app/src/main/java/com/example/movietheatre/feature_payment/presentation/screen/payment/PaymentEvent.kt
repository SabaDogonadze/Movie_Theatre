package com.example.movietheatre.feature_payment.presentation.screen.payment

sealed interface PaymentEvent {
    object LoadCards : PaymentEvent
    object AddNewCardClicked : PaymentEvent
    data class CardSelected(val index: Int) : PaymentEvent
    data class OnBuy(val screeningId: Int, val seats: List<String>, val totalPrice: Double) :
        PaymentEvent
}
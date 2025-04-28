package com.example.feature.payment.presentation.screen.payment

import android.content.Intent

sealed interface PaymentEvent {
    object LoadCards : PaymentEvent
    object AddNewCardClicked : PaymentEvent
    object GetCoins : PaymentEvent
    object SeenWarning : PaymentEvent
    data class OnDeleteCardClick(val cardNumber: String) : PaymentEvent
    data class OnChangeSelectedCoin(val coin: Int) : PaymentEvent


    data class OnBuy(val screeningId: Int, val seats: List<String>, val totalPrice: Double) :
        PaymentEvent

    object OnGooglePayClick : PaymentEvent

    data class OnGoogleBuy(
        val resultCode: Int,
        val data: Intent?,
        val screeningId: Int,
        val seats: List<String>,
        val totalPrice: Double,
    ) :
        PaymentEvent
}
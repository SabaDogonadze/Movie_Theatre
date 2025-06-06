package com.example.feature.payment.presentation.screen.payment

sealed interface PaymentSideEffect {
    object NavigateToAddCard : PaymentSideEffect
    data class ShowError(val message: Int) : PaymentSideEffect
    object NavigateToHomeScreen : PaymentSideEffect
    object SuccessfulPayment : PaymentSideEffect
    object SuccessfulDelete : PaymentSideEffect
}
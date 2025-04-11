package com.example.movietheatre.feature_payment.presentation.screen.add_card

sealed interface AddCardSideEffect {
    data class ShowError(val message: Int) : AddCardSideEffect
    object CardAddedSuccessfully : AddCardSideEffect
}
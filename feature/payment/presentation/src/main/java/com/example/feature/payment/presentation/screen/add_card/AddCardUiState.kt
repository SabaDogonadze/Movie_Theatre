package com.example.feature.payment.presentation.screen.add_card

import com.example.feature.payment.domain.model.CardType

data class AddCardUiState(
    val isLoading: Boolean = false,
    val cardHolderName: String = "",
    val cardHolderNameError: Int? = null,
    val cardNumber: String = "",
    val cardNumberError: Int? = null,
    val expiryDate: String = "",
    val expiryDateError: Int? = null,
    val cvv: String = "",
    val cvvError: Int? = null,
    val cardTypeSelected: CardType = CardType.MASTERCARD,
    val isAddCardEnabled: Boolean = false,
)
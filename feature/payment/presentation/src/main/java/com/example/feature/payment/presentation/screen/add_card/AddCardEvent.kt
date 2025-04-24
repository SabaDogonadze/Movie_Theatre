package com.example.feature.payment.presentation.screen.add_card

import com.example.feature.payment.domain.model.CardType

sealed interface AddCardEvent {
    data class CardHolderNameChanged(val text: String) : AddCardEvent
    data class CardNumberChanged(val text: String) : AddCardEvent
    data class ExpiryDateChanged(val text: String) : AddCardEvent
    data class CVVChanged(val text: String) : AddCardEvent
    data class CardTypeChanged(val cardType: CardType) :
        AddCardEvent
    object AddCardClicked : AddCardEvent
}
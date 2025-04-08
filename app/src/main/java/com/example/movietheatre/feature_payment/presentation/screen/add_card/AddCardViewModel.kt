package com.example.movietheatre.feature_payment.presentation.screen.add_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.R
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_payment.domain.use_case.AddCardUseCase
import com.example.movietheatre.feature_payment.domain.use_case.validation.ValidateCVVUseCase
import com.example.movietheatre.feature_payment.domain.use_case.validation.ValidateCardHolderNameUseCase
import com.example.movietheatre.feature_payment.domain.use_case.validation.ValidateCardNumberUseCase
import com.example.movietheatre.feature_payment.domain.use_case.validation.ValidateExpiryDateUseCase
import com.example.movietheatre.feature_payment.presentation.mapper.asStringResource
import com.example.movietheatre.feature_payment.presentation.mapper.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val validateCardHolderNameUseCase: ValidateCardHolderNameUseCase,
    private val validateCardNumberUseCase: ValidateCardNumberUseCase,
    private val validateExpiryDateUseCase: ValidateExpiryDateUseCase,
    private val validateCVVUseCase: ValidateCVVUseCase,
    private val addCardUseCase: AddCardUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddCardUiState())
    val uiState: StateFlow<AddCardUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<AddCardSideEffect>()
    val sideEffect: SharedFlow<AddCardSideEffect> = _sideEffect.asSharedFlow()

    fun onEvent(event: AddCardEvent) {
        when (event) {
            AddCardEvent.AddCardClicked -> {
                if (_uiState.value.isAddCardEnabled) {
                    addCard()
                } else {
                    viewModelScope.launch {
                        _sideEffect.emit(AddCardSideEffect.ShowError(R.string.please_correct_the_errors_before_proceeding))
                    }
                }
            }

            is AddCardEvent.CVVChanged -> {
                updateState(cvv = event.text)
            }

            is AddCardEvent.CardHolderNameChanged -> {
                updateState(cardHolderName = event.text)
            }

            is AddCardEvent.CardNumberChanged -> {
                updateState(cardNumber = event.text)
            }

            is AddCardEvent.CardTypeChanged -> _uiState.update { it.copy(cardTypeSelected = event.cardType) }
            is AddCardEvent.ExpiryDateChanged -> updateState(expiryDate = event.text)

        }
    }

    private fun updateState(
        cardHolderName: String? = null,
        cardNumber: String? = null,
        expiryDate: String? = null,
        cvv: String? = null,
    ) {
        val currentState = _uiState.value

        val newCardHolderName = cardHolderName ?: currentState.cardHolderName
        val newCardNumber = cardNumber ?: currentState.cardNumber
        val newExpiryDate = expiryDate ?: currentState.expiryDate
        val newCvv = cvv ?: currentState.cvv

        val holderValidation = validateCardHolderNameUseCase(newCardHolderName)
        val numberValidation = validateCardNumberUseCase(newCardNumber)
        val expiryValidation = validateExpiryDateUseCase(newExpiryDate)
        val cvvValidation = validateCVVUseCase(newCvv)

        val updatedState = currentState.copy(
            cardHolderName = newCardHolderName,
            cardHolderNameError = if (holderValidation is Resource.Error) holderValidation.error.asStringResource() else null,
            cardNumber = newCardNumber,
            cardNumberError = if (numberValidation is Resource.Error) numberValidation.error.asStringResource() else null,
            expiryDate = newExpiryDate,
            expiryDateError = if (expiryValidation is Resource.Error) expiryValidation.error.asStringResource() else null,
            cvv = newCvv,
            cvvError = if (cvvValidation is Resource.Error) cvvValidation.error.asStringResource() else null,
            isAddCardEnabled = (holderValidation is Resource.Success &&
                    numberValidation is Resource.Success &&
                    expiryValidation is Resource.Success &&
                    cvvValidation is Resource.Success)
        )

        _uiState.value = updatedState
    }

    private fun addCard() {

        val card = com.example.movietheatre.feature_payment.presentation.model.Card(
            cardNumber = _uiState.value.cardNumber,
            cardHolderName = _uiState.value.cardHolderName,
            expiryDate = _uiState.value.expiryDate,
            cvv = _uiState.value.cvv,
            cardType = _uiState.value.cardTypeSelected
        )
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = addCardUseCase(card.toDomain())) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false) }

                    _sideEffect.emit(AddCardSideEffect.CardAddedSuccessfully)
                }

                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffect.emit(AddCardSideEffect.ShowError(result.error.asStringResource()))
                }
            }
        }
    }
}
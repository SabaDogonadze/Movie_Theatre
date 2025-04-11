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
                onCVVChanged(event.text)
            }

            is AddCardEvent.CardHolderNameChanged -> {
                onCardHolderNameChanged(event.text)
            }

            is AddCardEvent.CardNumberChanged -> {
                onCardNumberChanged(event.text)
            }

            is AddCardEvent.CardTypeChanged -> {
                _uiState.update { it.copy(cardTypeSelected = event.cardType) }
            }

            is AddCardEvent.ExpiryDateChanged -> {
                onExpiryDateChanged(event.text)
            }
        }
    }

    private fun onCardHolderNameChanged(cardHolderName: String) {
        val validation = validateCardHolderNameUseCase(cardHolderName)
        val error = if (validation is Resource.Error) validation.error.asStringResource() else null

        _uiState.update { currentState ->
            currentState.copy(
                cardHolderName = cardHolderName,
                cardHolderNameError = error,
                isAddCardEnabled = isFormValid(
                    cardHolderNameValid = error == null && cardHolderName.isNotEmpty(),
                    cardNumberValid = currentState.cardNumberError == null && currentState.cardNumber.isNotEmpty(),
                    expiryDateValid = currentState.expiryDateError == null && currentState.expiryDate.isNotEmpty(),
                    cvvValid = currentState.cvvError == null && currentState.cvv.isNotEmpty()
                )
            )
        }
    }

    private fun onCardNumberChanged(cardNumber: String) {
        val validation = validateCardNumberUseCase(cardNumber)
        val error = if (validation is Resource.Error) validation.error.asStringResource() else null

        _uiState.update { currentState ->
            currentState.copy(
                cardNumber = cardNumber,
                cardNumberError = error,
                isAddCardEnabled = isFormValid(
                    cardHolderNameValid = currentState.cardHolderNameError == null && currentState.cardHolderName.isNotEmpty(),
                    cardNumberValid = error == null && cardNumber.isNotEmpty(),
                    expiryDateValid = currentState.expiryDateError == null && currentState.expiryDate.isNotEmpty(),
                    cvvValid = currentState.cvvError == null && currentState.cvv.isNotEmpty()
                )
            )
        }
    }

    private fun onExpiryDateChanged(expiryDate: String) {
        val validation = validateExpiryDateUseCase(expiryDate)
        val error = if (validation is Resource.Error) validation.error.asStringResource() else null

        _uiState.update { currentState ->
            currentState.copy(
                expiryDate = expiryDate,
                expiryDateError = error,
                isAddCardEnabled = isFormValid(
                    cardHolderNameValid = currentState.cardHolderNameError == null && currentState.cardHolderName.isNotEmpty(),
                    cardNumberValid = currentState.cardNumberError == null && currentState.cardNumber.isNotEmpty(),
                    expiryDateValid = error == null && expiryDate.isNotEmpty(),
                    cvvValid = currentState.cvvError == null && currentState.cvv.isNotEmpty()
                )
            )
        }
    }

    private fun onCVVChanged(cvv: String) {
        val validation = validateCVVUseCase(cvv)
        val error = if (validation is Resource.Error) validation.error.asStringResource() else null

        _uiState.update { currentState ->
            currentState.copy(
                cvv = cvv,
                cvvError = error,
                isAddCardEnabled = isFormValid(
                    cardHolderNameValid = currentState.cardHolderNameError == null && currentState.cardHolderName.isNotEmpty(),
                    cardNumberValid = currentState.cardNumberError == null && currentState.cardNumber.isNotEmpty(),
                    expiryDateValid = currentState.expiryDateError == null && currentState.expiryDate.isNotEmpty(),
                    cvvValid = error == null && cvv.isNotEmpty()
                )
            )
        }
    }

    private fun isFormValid(
        cardHolderNameValid: Boolean,
        cardNumberValid: Boolean,
        expiryDateValid: Boolean,
        cvvValid: Boolean,
    ): Boolean {
        return cardHolderNameValid && cardNumberValid && expiryDateValid && cvvValid
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
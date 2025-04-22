package com.example.movietheatre.feature_payment.presentation.screen.payment

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.R
import com.example.movietheatre.core.domain.use_case.GetCoinsUseCase
import com.example.movietheatre.core.domain.use_case.UpdateCoinUseCase
import com.example.movietheatre.core.domain.use_case.UpdateTicketUseCase
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.core.presentation.extension.toDomain
import com.example.movietheatre.core.presentation.util.TicketStatus
import com.example.movietheatre.feature_payment.domain.use_case.DeleteCardUseCase
import com.example.movietheatre.feature_payment.domain.use_case.GetCardsUseCase
import com.example.movietheatre.feature_payment.presentation.mapper.asStringResource
import com.example.movietheatre.feature_payment.presentation.mapper.toPresentation
import com.google.android.gms.wallet.PaymentData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
class PaymentViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val updateTicketUseCase: UpdateTicketUseCase,
    private val deleteCardUseCase: DeleteCardUseCase,
    private val getCoinsUseCase: GetCoinsUseCase,
    private val updateCoinUseCase: UpdateCoinUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<PaymentUiState> = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PaymentSideEffect>()
    val sideEffect: SharedFlow<PaymentSideEffect> = _sideEffect.asSharedFlow()


    init {
        onEvent(PaymentEvent.LoadCards)
        onEvent(PaymentEvent.GetCoins)

    }

    fun onEvent(event: PaymentEvent) {
        when (event) {
            is PaymentEvent.LoadCards -> {
                loadCards()
            }

            is PaymentEvent.AddNewCardClicked -> {
                viewModelScope.launch {
                    _sideEffect.emit(PaymentSideEffect.NavigateToAddCard)
                }
            }

            is PaymentEvent.OnBuy -> {
                onBuy(
                    screeningId = event.screeningId,
                    totalPrice = event.totalPrice,
                    seats = event.seats
                )
            }

            PaymentEvent.OnGooglePayClick -> _uiState.update { it.copy(isLoading = true) }
            is PaymentEvent.OnGoogleBuy -> {
                handlePaymentResult(
                    event.resultCode,
                    event.data,
                    event.screeningId,
                    event.seats,
                    event.totalPrice
                )
            }

            is PaymentEvent.OnDeleteCardClick -> {
                onDeleteCard(event.cardNumber)
            }

            is PaymentEvent.OnChangeSelectedCoin -> _uiState.update { it.copy(selectedCoins = event.coin) }
            PaymentEvent.GetCoins -> getCoins()
        }
    }

    private fun getCoins() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getCoinsUseCase()) {
                is Resource.Error -> {
                    _sideEffect.emit(PaymentSideEffect.ShowError(result.error.asStringResource()))
                    _uiState.update { it.copy(isLoading = false) }
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            userCoins = result.data.coins
                        )
                    }
                }
            }
        }
    }

    private fun loadCards() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            getCardsUseCase().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                        )
                        _sideEffect.emit(PaymentSideEffect.ShowError(result.error.asStringResource()))
                    }

                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            cards = result.data.map { it.toPresentation() },
                        )

                    }
                }
            }
        }
    }

    private fun onBuy(screeningId: Int, totalPrice: Double, seats: List<String>) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {

            if (totalPrice > 50) {
                _uiState.update { it.copy(isLoading = false) }
                _sideEffect.emit(PaymentSideEffect.ShowError(R.string.you_don_t_have_enough_money))
                return@launch
            }

            when (val bookedResult = updateTicketUseCase(
                screeningId = screeningId,
                seats = seats,
                status = TicketStatus.BOOKED.toDomain(),
                userId = firebaseAuth.currentUser!!.uid,
                discount = _uiState.value.selectedCoins.toDouble()
            )) {
                is Resource.Error -> {

                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffect.emit(PaymentSideEffect.ShowError(bookedResult.error.asStringResource()))
                }

                is Resource.Success -> {

                    when (val paymentResult = updateCoinUseCase(_uiState.value.selectedCoins)) {
                        is Resource.Error -> {
                            _sideEffect.emit(PaymentSideEffect.ShowError(paymentResult.error.asStringResource()))

                        }

                        is Resource.Success -> {
                            _sideEffect.emit(PaymentSideEffect.SuccessfulPayment)
                            delay(1000)
                            _sideEffect.emit(PaymentSideEffect.NavigateToHomeScreen)
                        }
                    }

                }
            }
        }
    }

    private fun onDeleteCard(cardNumber: String) {
        viewModelScope.launch {
            when (val result = deleteCardUseCase(cardNumber)) {
                is Resource.Error -> {
                    _sideEffect.emit(PaymentSideEffect.ShowError(result.error.asStringResource()))
                }

                is Resource.Success -> {
                    _sideEffect.emit(PaymentSideEffect.SuccessfulDelete)
                }
            }
        }
    }

    private fun handlePaymentResult(
        resultCode: Int,
        data: Intent?,
        screeningId: Int,
        seats: List<String>,
        totalPrice: Double,
    ) {
        Log.d("executed", "executed")
        viewModelScope.launch {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let { intent ->
                        PaymentData.getFromIntent(intent)?.let { paymentData ->
                            onEvent(
                                PaymentEvent.OnBuy(
                                    screeningId = screeningId,
                                    seats = seats,
                                    totalPrice = totalPrice
                                )
                            )
                        } ?: run {

                            _uiState.update { it.copy(isLoading = false) }
                            _sideEffect.emit(PaymentSideEffect.ShowError(R.string.error_in_payment_data_try_again))
                        }
                    }
                }

                Activity.RESULT_CANCELED -> {

                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffect.emit(PaymentSideEffect.ShowError(R.string.payment_cancelled))
                }

                else -> {

                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffect.emit(PaymentSideEffect.ShowError(R.string.payment_failed))
                }
            }
        }
    }
}
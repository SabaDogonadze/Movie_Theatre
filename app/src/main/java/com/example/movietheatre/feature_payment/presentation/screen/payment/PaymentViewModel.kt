package com.example.movietheatre.feature_payment.presentation.screen.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.R
import com.example.movietheatre.core.domain.use_case.UpdateTicketUseCase
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.core.presentation.extension.toDomain
import com.example.movietheatre.core.presentation.util.TicketStatus
import com.example.movietheatre.feature_payment.domain.use_case.GetCardsUseCase
import com.example.movietheatre.feature_payment.presentation.mapper.asStringResource
import com.example.movietheatre.feature_payment.presentation.mapper.toPresentation
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PaymentSideEffect>()
    val sideEffect: SharedFlow<PaymentSideEffect> = _sideEffect.asSharedFlow()

    init {
        onEvent(PaymentEvent.LoadCards)
    }

    fun onEvent(event: PaymentEvent) {
        when (event) {
            is PaymentEvent.LoadCards -> {
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                    when (val result = getCardsUseCase()) {
                        is Resource.Success -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                cards = result.data.map { it.toPresentation() },
                            )
                        }

                        is Resource.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                            )
                            _sideEffect.emit(PaymentSideEffect.ShowError(result.error.asStringResource()))
                        }
                    }
                }
            }

            is PaymentEvent.AddNewCardClicked -> {
                viewModelScope.launch {
                    _sideEffect.emit(PaymentSideEffect.NavigateToAddCard)
                }
            }

            is PaymentEvent.CardSelected -> {

            }

            is PaymentEvent.OnBuy -> {
                _uiState.update { it.copy(isLoading = true) }
                viewModelScope.launch {

                    if (event.totalPrice > 50) {
                        _uiState.update { it.copy(isLoading = false) }
                        _sideEffect.emit(PaymentSideEffect.ShowError(R.string.you_don_t_have_enough_money))
                        return@launch
                    }

                    when (val bookedResult = updateTicketUseCase(
                        screeningId = event.screeningId,
                        seats = event.seats,
                        status = TicketStatus.BOOKED.toDomain(),
                        userId = firebaseAuth.currentUser!!.uid
                    )) {
                        is Resource.Error -> {

                            _uiState.update { it.copy(isLoading = false) }
                            _sideEffect.emit(PaymentSideEffect.ShowError(bookedResult.error.asStringResource()))
                        }

                        is Resource.Success -> {
                            _uiState.update { it.copy(isLoading = false) }
                            _sideEffect.emit(PaymentSideEffect.SuccessfulPayment)
                            delay(1000)
                            _sideEffect.emit(PaymentSideEffect.NavigateToHomeScreen)
                        }
                    }
                }
            }
        }
    }
}
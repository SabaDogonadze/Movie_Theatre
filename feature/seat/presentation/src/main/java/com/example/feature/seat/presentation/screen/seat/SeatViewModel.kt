package com.example.feature.seat.presentation.screen.seat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.use_case.UpdateTicketUseCase
import com.example.core.domain.util.Resource
import com.example.core.presentation.extension.asStringResource
import com.example.core.presentation.extension.toDomain
import com.example.core.presentation.util.TicketStatus
import com.example.feature.seat.domain.use_case.GetSeatsUseCase
import com.example.feature.seat.presentation.mapper.toPresentation
import com.example.feature.seat.presentation.model.Seat
import com.example.feature.seat.presentation.util.SeatType
import com.example.core.presentation.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatViewModel @Inject constructor(
    private val getSeatsUseCase: GetSeatsUseCase,
    private val updateTicketUseCase: UpdateTicketUseCase,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeatUiState())
    val uiState: StateFlow<SeatUiState> = _uiState

    private val _sideEffects = MutableSharedFlow<SeatSideEffect>()
    val sideEffects = _sideEffects.asSharedFlow()

    fun onEvent(event: SeatUiEvent) {
        when (event) {
            is SeatUiEvent.GetSeats -> getSeats(event.screeningId)
            is SeatUiEvent.UpdateSeat -> toggleSeatSelection(event.seat)
            is SeatUiEvent.BookTicket -> bookTicket(event.screeningId)
            is SeatUiEvent.BuyTicket -> buyOptions(event.screeningId, event.ticketPrice)
        }
    }

    private fun toggleSeatSelection(selectedSeat: Seat) {
        _uiState.update { state ->
            state.copy(
                seats = state.seats.map { seat ->
                    if (seat.id == selectedSeat.id) {
                        seat.copy(status = if (seat.status == SeatType.SELECTED) SeatType.FREE else SeatType.SELECTED)
                    } else seat
                }
            )
        }
    }

    private fun getSeats(screeningId: Int) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getSeatsUseCase(screeningId)) {
                is Resource.Error -> {
                    _sideEffects.emit(SeatSideEffect.ShowError(result.error.asStringResource()))
                    _uiState.update { it.copy(isLoading = false) }
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            seats = result.data.map { it.toPresentation() }
                        )
                    }
                }
            }
        }
    }

    private fun bookTicket(screeningId: Int) {
        viewModelScope.launch {
            val selectedSeats = _uiState.value.seats.filter { it.status == SeatType.SELECTED }
            if (selectedSeats.isEmpty()) {
                _sideEffects.emit(SeatSideEffect.ShowError(R.string.please_select_the_seats))
                return@launch
            }
            _uiState.update { it.copy(isLoading = true) }

            when (val result = updateTicketUseCase(
                screeningId = screeningId,
                seats = selectedSeats.map { it.seatNumber },
                status = TicketStatus.HELD.toDomain(),
                userId = firebaseAuth.currentUser!!.uid
            )) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffects.emit(SeatSideEffect.ShowError(result.error.asStringResource()))
                }

                is Resource.Success -> {
                    _sideEffects.emit(SeatSideEffect.ShowSuccessfulHoldScreen)
                    delay(1000)
                    _sideEffects.emit(SeatSideEffect.NavigateToDetailScreen)
                }
            }
        }
    }

    private fun buyOptions(screeningId: Int, ticketPrice: Double) {
        viewModelScope.launch {
            val selectedSeats = _uiState.value.seats.filter { it.status == SeatType.SELECTED }
            if (selectedSeats.isEmpty()) {
                _sideEffects.emit(SeatSideEffect.ShowError(R.string.please_select_the_seats))
                return@launch
            }

            val totalCost = selectedSeats.sumOf { it.vipAddOn + ticketPrice }


            _uiState.update { it.copy(isLoading = true) }
            when (val holdResult = updateTicketUseCase(
                screeningId = screeningId,
                seats = selectedSeats.map { it.seatNumber },
                status = TicketStatus.HELD.toDomain(),
                userId = firebaseAuth.currentUser!!.uid
            )) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffects.emit(SeatSideEffect.ShowError(holdResult.error.asStringResource()))
                    return@launch
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffects.emit(
                        SeatSideEffect.NavigateToPaymentScreen(
                            seats = selectedSeats.map { it.seatNumber },
                            totalPrice = totalCost
                        )
                    )
                }
            }
        }
    }
}
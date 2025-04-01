package com.example.movietheatre.feature_seat.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.R
import com.example.movietheatre.core.domain.use_case.UpdateTicketUseCase
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.core.presentation.extension.toDomain
import com.example.movietheatre.core.presentation.util.TicketStatus
import com.example.movietheatre.feature_seat.domain.use_case.GetSeatsUseCase
import com.example.movietheatre.feature_seat.presentation.mapper.toPresentation
import com.example.movietheatre.feature_seat.presentation.model.Seat
import com.example.movietheatre.feature_seat.presentation.util.SeatType
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
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(SeatUiState())
    val uiState: StateFlow<SeatUiState> = _uiState

    private val _sideEffects = MutableSharedFlow<SeatSideEffect>()
    val sideEffects = _sideEffects.asSharedFlow()

    fun onEvent(event: SeatUiEvent) {
        when (event) {
            is SeatUiEvent.GetSeats -> getSeats(event.screeningId)
            is SeatUiEvent.UpdateSeat -> updateSeat(selectedSeat = event.seat)
            is SeatUiEvent.UpdateTicker -> updateTicket(event.screeningId, event.status)
        }
    }

    private fun updateTicket(
        screeningId: Int,
        ticketStatus: TicketStatus,
    ) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {

            if (_uiState.value.seats.none { it.status == SeatType.SELECTED }) {
                _sideEffects.emit(SeatSideEffect.ShowError(R.string.please_select_the_seats))
                return@launch
            }

            when (val result = updateTicketUseCase(
                screeningId = screeningId,
                seats = _uiState.value.seats.filter { it.status == SeatType.SELECTED }
                    .map { it.seatNumber },
                status = ticketStatus.toDomain(),
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
                            seats = result.data.map { it.toPresentation() })
                    }
                }
            }
        }
    }

    private fun updateSeat(selectedSeat: Seat) {
        _uiState.update { state ->
            state.copy(
                seats = state.seats.map { seat ->
                    if (seat.id == selectedSeat.id) {
                        val newStatus =
                            if (seat.status == SeatType.SELECTED) SeatType.FREE else SeatType.SELECTED
                        seat.copy(status = newStatus)
                    } else seat
                }
            )
        }
    }
}
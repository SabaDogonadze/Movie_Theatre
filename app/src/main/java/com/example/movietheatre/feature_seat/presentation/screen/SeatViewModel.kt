package com.example.movietheatre.feature_seat.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.feature_seat.domain.use_case.GetSeatsUseCase
import com.example.movietheatre.feature_seat.presentation.mapper.toPresentation
import com.example.movietheatre.feature_seat.presentation.model.Seat
import com.example.movietheatre.feature_seat.presentation.util.SeatType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatViewModel @Inject constructor(private val getSeatsUseCase: GetSeatsUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(SeatUiState())
    val uiState: StateFlow<SeatUiState> = _uiState

    private val _sideEffects = MutableSharedFlow<SeatSideEffect>()
    val sideEffects = _sideEffects.asSharedFlow()

    fun onEvent(event: SeatUiEvent) {
        when (event) {
            SeatUiEvent.GetSeats -> getSeats()
            is SeatUiEvent.UpdateSeat -> updateSeat(selectedSeat = event.seat)
        }
    }

    private fun getSeats() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = getSeatsUseCase(3)) {
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
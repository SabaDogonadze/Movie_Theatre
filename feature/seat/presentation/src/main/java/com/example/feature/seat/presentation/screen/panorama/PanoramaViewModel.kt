package com.example.feature.seat.presentation.screen.panorama

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.util.Resource
import com.example.core.presentation.extension.asStringResource
import com.example.feature.seat.domain.use_case.GetSeatsPanoramaUseCase
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
class PanoramaViewModel @Inject constructor(
    private val getSeatsPanoramaUseCase: GetSeatsPanoramaUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow(PanoramaUiState())
    val uiState: StateFlow<PanoramaUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PanoramaSideEffect>()
    val sideEffect: SharedFlow<PanoramaSideEffect> = _sideEffect.asSharedFlow()

    fun handleEvent(event: PanoramaEvent) {
        when (event) {
            is PanoramaEvent.LoadSeatsWithPanorama -> loadSeatsWithPanorama(
                event.screeningId,
                event.seats
            )

            is PanoramaEvent.NavigateToSeat -> navigateToSeat(event.index)
        }
    }

    private fun loadSeatsWithPanorama(screeningId: Int, seats: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getSeatsPanoramaUseCase(screeningId, seats)) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            panoramaSeats = result.data,
                            isLoading = false,
                            currentSeatIndex = 0
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    _sideEffect.emit(PanoramaSideEffect.ShowError(result.error.asStringResource()))
                }
            }
        }
    }

    private fun navigateToSeat(index: Int) {
        viewModelScope.launch {
            if (index in 0 until _uiState.value.panoramaSeats.size) {
                _uiState.update { it.copy(currentSeatIndex = index) }
                _sideEffect.emit(PanoramaSideEffect.NavigatedToNewSeat)
            }
        }
    }
}
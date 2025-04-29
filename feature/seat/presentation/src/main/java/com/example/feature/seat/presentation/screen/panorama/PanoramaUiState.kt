package com.example.feature.seat.presentation.screen.panorama

import com.example.feature.seat.domain.model.GetSeat

data class PanoramaUiState(
    val panoramaSeats: List<GetSeat> = emptyList(),
    val currentSeatIndex: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val currentSeatPanoramaUrl: String?
        get() = panoramaSeats.getOrNull(currentSeatIndex)?.imgUrl

    val currentSeatNumber: String?
        get() = panoramaSeats.getOrNull(currentSeatIndex)?.seatNumber

    val hasMultipleSeats: Boolean
        get() = panoramaSeats.size > 1

    val canNavigateNext: Boolean
        get() = currentSeatIndex < panoramaSeats.size - 1

    val canNavigatePrevious: Boolean
        get() = currentSeatIndex > 0
}

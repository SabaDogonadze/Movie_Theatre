package com.example.feature.seat.presentation.screen.panorama

sealed interface PanoramaEvent {
    data class LoadSeatsWithPanorama(val screeningId: Int, val seats: String) : PanoramaEvent
    data class NavigateToSeat(val index: Int) : PanoramaEvent
}

package com.example.feature.seat.presentation.screen.panorama

sealed class PanoramaSideEffect {
    data class ShowError(val message: Int) : PanoramaSideEffect()
    object NavigatedToNewSeat : PanoramaSideEffect()
}
package com.example.movietheatre.feature_home.presentation.event

sealed class HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect()
    //data object NavigateToDetail : HomeSideEffect()
}
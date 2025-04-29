package com.example.feature.home.presentation.event

sealed class HomeSideEffect {
    data class ShowError(val message: Int) : HomeSideEffect()
    //data object NavigateToDetail : HomeSideEffect()
}
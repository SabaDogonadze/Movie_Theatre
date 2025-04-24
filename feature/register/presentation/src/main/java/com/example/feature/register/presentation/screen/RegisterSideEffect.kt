package com.example.feature.register.presentation.screen

sealed interface RegisterSideEffect {
    object NavigateToLoginScreen : RegisterSideEffect
    data class ShowSnackBar(val message: Int) : RegisterSideEffect
}
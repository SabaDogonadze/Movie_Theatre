package com.example.movietheatre.feature_register.presentation.screen

import com.example.movietheatre.core.domain.util.error.RegisterError

sealed interface RegisterSideEffect {
    object NavigateToLoginScreen : RegisterSideEffect
    data class ShowSnackBar(val message: RegisterError) : RegisterSideEffect
}
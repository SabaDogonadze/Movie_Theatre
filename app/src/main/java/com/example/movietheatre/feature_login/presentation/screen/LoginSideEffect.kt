package com.example.movietheatre.feature_login.presentation.screen

import com.example.movietheatre.core.domain.util.error.LoginError

sealed interface LoginSideEffect {
    object SuccessFullLogin : LoginSideEffect
    data class ShowSnackBar(val message: LoginError) : LoginSideEffect
}
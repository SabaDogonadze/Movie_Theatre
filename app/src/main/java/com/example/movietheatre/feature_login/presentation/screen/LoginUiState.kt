package com.example.movietheatre.feature_login.presentation.screen

import com.example.movietheatre.core.domain.util.error.EmailError
import com.example.movietheatre.core.domain.util.error.PasswordError

data class LoginUiState(
    val isLoading: Boolean = false,
    val emailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isValidForm: Boolean = false,
)
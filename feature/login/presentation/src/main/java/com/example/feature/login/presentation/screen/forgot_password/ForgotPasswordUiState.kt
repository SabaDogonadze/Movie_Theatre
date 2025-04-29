package com.example.feature.login.presentation.screen.forgot_password

import com.example.feaute.login.domain.util.ForgotPasswordError


data class ForgotPasswordUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val emailError: Int? = null,
    val forgotPasswordError: ForgotPasswordError? = null,
)
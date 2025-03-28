package com.example.movietheatre.feature_login.presentation.screen

data class LoginUiState(
    val isLoading: Boolean = false,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isValidForm: Boolean = false,

    )
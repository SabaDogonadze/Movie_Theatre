package com.example.movietheatre.feature_login.presentation.screen.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val rememberMeChecked: Boolean = false,
    val passwordIsVisible: Boolean = false,
    val isLoading: Boolean = false,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isValidForm: Boolean = false,

    )
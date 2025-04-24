package com.example.feature.register.presentation.screen

data class RegisterUiState(
    val isLoading: Boolean = false,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val repeatedPasswordError: Int? = null,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isRepeatedPasswordValid: Boolean = false,
    val isValidForm: Boolean = false,
)
package com.example.movietheatre.feature_register.presentation.screen

import com.example.movietheatre.core.domain.util.error.EmailError
import com.example.movietheatre.core.domain.util.error.PasswordError
import com.example.movietheatre.core.domain.util.error.RepeatPasswordError

data class RegisterUiState(
    val isLoading: Boolean = false,
    val emailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val repeatedPasswordError: RepeatPasswordError? =null,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isRepeatedPasswordValid: Boolean = false,
    val isValidForm: Boolean = false,
)
package com.example.feature.login.presentation.screen.login

sealed interface LoginEvent {
    data  object Login : LoginEvent
    data class ValidateEmail(val email: String) : LoginEvent
    data class ValidatePassword(val password: String) : LoginEvent
    data object PasswordVisibilityChanged : LoginEvent
    data object RememberMeChanged : LoginEvent
}
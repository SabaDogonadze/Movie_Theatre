package com.example.movietheatre.feature_login.presentation.screen.login

sealed interface LoginEvent {
    data class Login(val email: String, val password: String, val rememberMe: Boolean) : LoginEvent
    data class ValidateEmail(val email: String) : LoginEvent
    data class ValidatePassword(val password: String) : LoginEvent
}
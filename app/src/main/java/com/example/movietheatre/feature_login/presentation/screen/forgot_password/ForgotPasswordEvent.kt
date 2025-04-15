package com.example.movietheatre.feature_login.presentation.screen.forgot_password

sealed class ForgotPasswordEvent {
    data class EmailChanged(val email: String) : ForgotPasswordEvent()
    object ResetPasswordClicked : ForgotPasswordEvent()
}
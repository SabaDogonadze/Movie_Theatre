package com.example.feature.login.presentation.screen.forgot_password

sealed class ForgotPasswordEvent {
    data class EmailChanged(val email: String) : ForgotPasswordEvent()
    object ResetPasswordClicked : ForgotPasswordEvent()
}
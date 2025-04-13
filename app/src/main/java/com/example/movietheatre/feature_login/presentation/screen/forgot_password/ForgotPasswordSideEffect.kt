package com.example.movietheatre.feature_login.presentation.screen.forgot_password

sealed class ForgotPasswordSideEffect {
    data class ShowError(val message: Int) : ForgotPasswordSideEffect()
    data class ShowSuccess(val message: Int) : ForgotPasswordSideEffect()
    object NavigateBackToLogin : ForgotPasswordSideEffect()
}
package com.example.feature.login.presentation.screen.login


sealed interface LoginSideEffect {
     object SuccessFullLogin : LoginSideEffect
    data class ShowSnackBar(val message: Int) : LoginSideEffect
}
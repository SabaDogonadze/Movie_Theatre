package com.example.movietheatre.feature_login.presentation.screen.login


sealed interface LoginSideEffect {
     object SuccessFullLogin : LoginSideEffect
    data class ShowSnackBar(val message: Int) : LoginSideEffect
}
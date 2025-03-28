package com.example.movietheatre.feature_login.domain.util

import com.example.movietheatre.core.domain.util.error.RootError

sealed class LoginError : RootError {
    object NoInternetError : LoginError()
    object UserNotFound : LoginError()
    object InvalidCredentials : LoginError()
    data object UnknownError : LoginError()
}
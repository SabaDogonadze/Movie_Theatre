package com.example.movietheatre.core.domain.util.error


sealed class LoginError : RootError {
    object NoInternetError : LoginError()
    object UserNotFound : LoginError()
    data object UnknownError : LoginError()
}
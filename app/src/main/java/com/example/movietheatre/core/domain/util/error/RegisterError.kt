package com.example.movietheatre.core.domain.util.error

sealed class RegisterError : RootError {
    object NoInternetError : RegisterError()
    object EmailAlreadyExists : RegisterError()
    data object UnknownError : RegisterError()
}
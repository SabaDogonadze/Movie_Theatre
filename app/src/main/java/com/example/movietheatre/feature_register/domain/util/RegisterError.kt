package com.example.movietheatre.feature_register.domain.util

import com.example.movietheatre.core.domain.util.error.RootError

sealed class RegisterError : RootError {
    object NoInternetError : RegisterError()
    object EmailAlreadyExists : RegisterError()
    data object UnknownError : RegisterError()
}
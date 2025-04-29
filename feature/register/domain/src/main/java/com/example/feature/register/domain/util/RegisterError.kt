package com.example.feature.register.domain.util

import com.example.core.domain.util.error.RootError


sealed class RegisterError : RootError {
    object NoInternetError : RegisterError()
    object EmailAlreadyExists : RegisterError()
    data object UnknownError : RegisterError()
}
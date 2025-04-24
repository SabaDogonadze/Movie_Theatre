package com.example.feaute.login.domain.util

import com.example.core.domain.util.error.RootError


sealed class LoginError : RootError {
    object NoInternetError : LoginError()
    object UserNotFound : LoginError()
    object InvalidCredentials : LoginError()
    data object UnknownError : LoginError()
}
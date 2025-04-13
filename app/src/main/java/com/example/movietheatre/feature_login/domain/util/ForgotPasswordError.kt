package com.example.movietheatre.feature_login.domain.util

import com.example.movietheatre.core.domain.util.error.RootError

enum class ForgotPasswordError : RootError {
    NETWORK_ERROR,
    USER_NOT_FOUND,
    TOO_MANY_REQUEST,
    UNKNOWN_ERROR,
}
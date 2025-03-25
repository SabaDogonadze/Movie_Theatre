package com.example.movietheatre.feature_login.presentation.extension

import com.example.movietheatre.R
import com.example.movietheatre.core.domain.util.error.LoginError

fun LoginError.asStringResource(): Int {
    return when (this) {
        LoginError.NoInternetError -> R.string.no_internet
        LoginError.UnknownError -> R.string.unknown_error
        LoginError.UserNotFound -> R.string.user_not_found
    }
}
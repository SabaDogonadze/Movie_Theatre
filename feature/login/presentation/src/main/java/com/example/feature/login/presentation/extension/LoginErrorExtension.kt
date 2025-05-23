package com.example.feature.login.presentation.extension

import com.example.feaute.login.domain.util.LoginError
import com.example.core.presentation.R

fun LoginError.asStringResource(): Int {
    return when (this) {
        LoginError.NoInternetError -> R.string.no_internet
        LoginError.UnknownError -> R.string.unknown_error
        LoginError.UserNotFound -> R.string.user_not_found
        LoginError.InvalidCredentials -> R.string.invalid_credintials
    }
}
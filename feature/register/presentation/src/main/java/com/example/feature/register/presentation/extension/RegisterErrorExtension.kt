package com.example.feature.register.presentation.extension

import com.example.feature.register.domain.util.RegisterError
import com.example.core.presentation.R


fun RegisterError.asStringResource(): Int {
    return when (this) {
        RegisterError.EmailAlreadyExists -> R.string.email_already_exits
        RegisterError.NoInternetError -> R.string.no_internet
        RegisterError.UnknownError -> R.string.unknown_error
    }
}
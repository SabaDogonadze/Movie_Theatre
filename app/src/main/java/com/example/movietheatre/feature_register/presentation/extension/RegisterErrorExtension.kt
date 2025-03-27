package com.example.movietheatre.feature_register.presentation.extension

import com.example.movietheatre.R
import com.example.movietheatre.feature_register.domain.util.RegisterError


fun RegisterError.asStringResource(): Int {
 return   when (this) {
        RegisterError.EmailAlreadyExists -> R.string.email_already_exits
        RegisterError.NoInternetError -> R.string.no_internet
        RegisterError.UnknownError -> R.string.unknown_error
    }
}
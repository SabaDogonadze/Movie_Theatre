package com.example.movietheatre.feature_register.domain.util

import com.example.movietheatre.core.domain.util.error.RootError

enum class RepeatPasswordError : RootError {
    NO_MATCH,
    BLANK_FIELD,
}
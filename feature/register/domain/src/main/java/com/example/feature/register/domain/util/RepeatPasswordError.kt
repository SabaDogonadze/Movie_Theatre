package com.example.feature.register.domain.util

import com.example.core.domain.util.error.RootError


enum class RepeatPasswordError : RootError {
    NO_MATCH,
    BLANK_FIELD,
}
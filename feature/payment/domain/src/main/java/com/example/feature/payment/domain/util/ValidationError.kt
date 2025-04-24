package com.example.feature.payment.domain.util

import com.example.core.domain.util.error.RootError

enum class CardHolderNameError : RootError {
    EMPTY,
    INVALID_CHARACTERS
}

enum class CardNumberError : RootError {
    EMPTY,
    INVALID_FORMAT,
    FAILED_LUHN_CHECK
}

enum class ExpiryDateError : RootError {
    EMPTY,
    INVALID_FORMAT,
    EXPIRED,
    INVALID_DATE
}

enum class CVVError : RootError {
    EMPTY,
    INVALID_FORMAT
}
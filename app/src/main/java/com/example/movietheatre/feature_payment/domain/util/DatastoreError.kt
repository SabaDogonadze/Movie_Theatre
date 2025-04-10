package com.example.movietheatre.feature_payment.domain.util

import com.example.movietheatre.core.domain.util.error.RootError

enum class AddCardError : RootError {
    UNKNOWN,
    ALREADY_EXISTS
}

enum class DatastoreError : RootError {
    UNKNOWN
}
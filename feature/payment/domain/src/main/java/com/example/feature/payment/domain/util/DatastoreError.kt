package com.example.feature.payment.domain.util

import com.example.core.domain.util.error.RootError

enum class AddCardError : RootError {
    UNKNOWN,
    ALREADY_EXISTS
}

enum class DatastoreError : RootError {
    UNKNOWN
}
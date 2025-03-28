package com.example.movietheatre.core.presentation.extension

import com.example.movietheatre.R
import com.example.movietheatre.core.domain.util.error.EmailError
import com.example.movietheatre.core.domain.util.error.PasswordError

/**
 * this file is to map common error types to string resources
 * */

fun PasswordError.asStringResource(): Int {
    return when (this) {
        PasswordError.SHORT_PASSWORD -> R.string.the_password_needs_to_consist_of_at_least_8_characters
        PasswordError.INVALID_PASSWORD -> R.string.the_password_needs_to_contain_at_least_one_letter_and_digit
    }
}

fun EmailError.asStringResource(): Int {
    return when (this) {
        EmailError.INVALID_EMAIL -> R.string.invalid_email
        EmailError.BLANK_FIELD -> R.string.field_can_not_be_blank
    }
}


package com.example.movietheatre.feature_payment.presentation.mapper

import com.example.movietheatre.R
import com.example.movietheatre.feature_payment.domain.util.CVVError
import com.example.movietheatre.feature_payment.domain.util.CardHolderNameError
import com.example.movietheatre.feature_payment.domain.util.CardNumberError
import com.example.movietheatre.feature_payment.domain.util.DatastoreError
import com.example.movietheatre.feature_payment.domain.util.ExpiryDateError


fun CardHolderNameError.asStringResource(): Int {
    return when (this) {
        CardHolderNameError.EMPTY -> R.string.name_cannot_be_empty
        CardHolderNameError.INVALID_CHARACTERS -> R.string.name_contains_invalid_characters
    }
}

fun CardNumberError.asStringResource(): Int {
    return when (this) {
        CardNumberError.EMPTY -> R.string.card_number_cannot_be_empty
        CardNumberError.INVALID_FORMAT -> R.string.card_number_must_be_16_digits
        CardNumberError.FAILED_LUHN_CHECK -> R.string.invalid_card_number
    }
}

fun ExpiryDateError.asStringResource(): Int {
    return when (this) {
        ExpiryDateError.EMPTY -> R.string.expiry_date_cannot_be_empty
        ExpiryDateError.INVALID_FORMAT -> R.string.expiry_date_must_be_in_mm_yy_format
        ExpiryDateError.EXPIRED -> R.string.card_has_expired
        ExpiryDateError.INVALID_DATE -> R.string.expiry_date_is_not_realistic

    }

}

fun CVVError.asStringResource(): Int {
    return when (this) {
        CVVError.EMPTY -> R.string.cvv_cannot_be_empty
        CVVError.INVALID_FORMAT -> R.string.cvv_must_be_exactly_3_digits

    }

}

fun DatastoreError.asStringResource(): Int {
    return when (this) {
        DatastoreError.UNKNOWN -> R.string.can_not_save_value_plz_try_again
    }
}
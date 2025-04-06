package com.example.movietheatre.feature_payment.domain.use_case.validation

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_payment.domain.extension.luhnCheck
import com.example.movietheatre.feature_payment.domain.util.CardNumberError
import javax.inject.Inject

class ValidateCardNumberUseCase @Inject constructor() {

    operator fun invoke(number: String): Resource<Unit, CardNumberError> {
        val trimmed = number.filter { !it.isWhitespace() }
        if (trimmed.isEmpty()) {
            return Resource.Error(CardNumberError.EMPTY)
        }
        // Here we assume a fixed length for simplicity; adjust as necessary.
        if (!trimmed.matches(Regex("^\\d{16}\$"))) {
            return Resource.Error(CardNumberError.INVALID_FORMAT)
        }
        if (!(trimmed).luhnCheck()) {
            return Resource.Error(CardNumberError.FAILED_LUHN_CHECK)
        }
        return Resource.Success(Unit)
    }
}
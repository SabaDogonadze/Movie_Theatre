package com.example.feature.payment.domain.use_case.validation

import com.example.core.domain.util.Resource
import com.example.feature.payment.domain.extension.luhnCheck
import com.example.feature.payment.domain.util.CardNumberError
import javax.inject.Inject

class ValidateCardNumberUseCase @Inject constructor() {

    operator fun invoke(number: String): Resource<Unit, CardNumberError> {
        val trimmed = number.filter { !it.isWhitespace() }
        if (trimmed.isEmpty()) {
            return Resource.Error(CardNumberError.EMPTY)
        }

        if (!trimmed.matches(Regex("^\\d{16}\$"))) {
            return Resource.Error(CardNumberError.INVALID_FORMAT)
        }
        if (!(trimmed).luhnCheck()) {
            return Resource.Error(CardNumberError.FAILED_LUHN_CHECK)
        }
        return Resource.Success(Unit)
    }
}
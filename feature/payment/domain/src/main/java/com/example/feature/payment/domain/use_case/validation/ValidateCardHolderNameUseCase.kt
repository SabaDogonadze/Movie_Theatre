package com.example.feature.payment.domain.use_case.validation

import com.example.core.domain.util.Resource
import com.example.feature.payment.domain.util.CardHolderNameError
import javax.inject.Inject

class ValidateCardHolderNameUseCase @Inject constructor() {

    private val validNamePattern = Regex("^[a-zA-Z\\s'-]+\$")

    operator fun invoke(name: String): Resource<Unit, CardHolderNameError> {
        if (name.isBlank()) {
            return Resource.Error(CardHolderNameError.EMPTY)
        }
        if (!validNamePattern.matches(name.trim())) {
            return Resource.Error(CardHolderNameError.INVALID_CHARACTERS)
        }
        return Resource.Success(Unit)
    }
}
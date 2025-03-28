package com.example.movietheatre.core.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.EmailError
import javax.inject.Inject

class ValidateEmailUseCase@Inject constructor() {
    operator fun invoke(email: String): Resource<Unit, EmailError> {
        if (email.isBlank()) {
            return Resource.Error(EmailError.BLANK_FIELD)
        }
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val isValid = emailRegex.toRegex().matches(email)

        if (!isValid) {
            return Resource.Error(EmailError.INVALID_EMAIL)
        }
        return Resource.Success(Unit)
    }
}
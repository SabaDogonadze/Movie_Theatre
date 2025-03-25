package com.example.movietheatre.core.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.PasswordError
import javax.inject.Inject

class ValidatePasswordUseCase@Inject constructor() {
    operator fun invoke(password: String): Resource<Unit, PasswordError> {
        if (password.length < 8) {
            return Resource.Error(PasswordError.SHORT_PASSWORD)
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return Resource.Error(PasswordError.INVALID_PASSWORD)
        }
        return Resource.Success(Unit)
    }


}
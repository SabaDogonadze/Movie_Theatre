package com.example.feature.payment.domain.use_case.validation

import com.example.core.domain.util.Resource
import com.example.feature.payment.domain.util.CVVError
import javax.inject.Inject

class ValidateCVVUseCase @Inject constructor() {

    operator fun invoke(cvv: String): Resource<Unit, CVVError> {
        val trimmed = cvv.trim()
        if (trimmed.isEmpty()) {
            return Resource.Error(CVVError.EMPTY)
        }
        if (!trimmed.matches(Regex("^\\d{3}\$"))) {
            return Resource.Error(CVVError.INVALID_FORMAT)
        }
        return Resource.Success(Unit)
    }
}
package com.example.feature.payment.domain.use_case.validation

import com.example.core.domain.util.Resource
import com.example.feature.payment.domain.util.ExpiryDateError
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

class ValidateExpiryDateUseCase @Inject constructor() {

    private val formatter = DateTimeFormatter.ofPattern("MM/yy")

    operator fun invoke(expiryDate: String): Resource<Unit, ExpiryDateError> {
        if (expiryDate.isBlank()) {
            return Resource.Error(ExpiryDateError.EMPTY)
        }
        if (!expiryDate.matches(Regex("^(0[1-9]|1[0-2])/\\d{2}\$"))) {
            return Resource.Error(ExpiryDateError.INVALID_FORMAT)
        }
        try {
            val yearMonth = YearMonth.parse(expiryDate, formatter)
            val currentYearMonth = YearMonth.now()

            if (yearMonth.year < currentYearMonth.year - 1 || yearMonth.year > currentYearMonth.year + 20) {
                return Resource.Error(ExpiryDateError.INVALID_DATE)
            }
            if (yearMonth < currentYearMonth) {
                return Resource.Error(ExpiryDateError.EXPIRED)
            }
        } catch (e: DateTimeParseException) {
            return Resource.Error(ExpiryDateError.INVALID_FORMAT)
        }
        return Resource.Success(Unit)
    }
}
package com.example.feature.register.domain.use_case

import com.example.core.domain.use_case.ValidateEmailUseCase
import com.example.core.domain.use_case.ValidatePasswordUseCase


data class RegisterUseCaseWrapper(
    val registerUseCase: RegisterUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateRepeatPasswordUseCase: ValidateRepeatPasswordUseCase,
)
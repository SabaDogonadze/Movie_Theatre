package com.example.movietheatre.feature_register.domain.use_case

import com.example.movietheatre.core.domain.use_case.ValidateEmailUseCase
import com.example.movietheatre.core.domain.use_case.ValidatePasswordUseCase

data class RegisterUseCaseWrapper(
    val registerUseCase: RegisterUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateRepeatPasswordUseCase: ValidateRepeatPasswordUseCase
)
package com.example.movietheatre.feature_login.domain.use_case

import com.example.movietheatre.core.domain.use_case.SaveValueToLocalStorageUseCase
import com.example.movietheatre.core.domain.use_case.ValidateEmailUseCase
import com.example.movietheatre.core.domain.use_case.ValidatePasswordUseCase
/**
 * helper wrapper class for inject dependencies  in viewmodel
 * */
data class LoginUseCaseWrapper(
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val loginUseCase: LoginUseCase,
    val saveValueToLocalStorageUseCase: SaveValueToLocalStorageUseCase,
)
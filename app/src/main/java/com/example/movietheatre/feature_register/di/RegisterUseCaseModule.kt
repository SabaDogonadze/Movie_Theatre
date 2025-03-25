package com.example.movietheatre.feature_register.di

import com.example.movietheatre.core.domain.use_case.ValidateEmailUseCase
import com.example.movietheatre.core.domain.use_case.ValidatePasswordUseCase
import com.example.movietheatre.feature_register.domain.use_case.RegisterUseCase
import com.example.movietheatre.feature_register.domain.use_case.RegisterUseCaseWrapper
import com.example.movietheatre.feature_register.domain.use_case.ValidateRepeatPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RegisterUseCaseModule {
    @Provides
    fun provideRegisterUserCaseWrapper(
        registerUseCase: RegisterUseCase,
        validateRepeatPasswordUseCase: ValidateRepeatPasswordUseCase,
        validateEmailUseCase: ValidateEmailUseCase,
        passwordUseCase: ValidatePasswordUseCase,
    ): RegisterUseCaseWrapper {
        return RegisterUseCaseWrapper(
            registerUseCase = registerUseCase,
            validateEmailUseCase = validateEmailUseCase,
            validatePasswordUseCase = passwordUseCase,
            validateRepeatPasswordUseCase = validateRepeatPasswordUseCase
        )
    }
}
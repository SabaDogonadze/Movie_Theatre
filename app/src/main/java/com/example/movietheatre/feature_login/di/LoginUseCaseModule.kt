package com.example.movietheatre.feature_login.di

import com.example.movietheatre.core.domain.use_case.ValidateEmailUseCase
import com.example.movietheatre.core.domain.use_case.ValidatePasswordUseCase
import com.example.movietheatre.feature_login.domain.use_case.LoginUseCase
import com.example.movietheatre.feature_login.domain.use_case.LoginUseCaseWrapper
import com.example.movietheatre.feature_login.domain.use_case.SaveValueToLocalStorageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LoginUseCaseModule {
    @Provides
    fun provideLoginUserCaseWrapper(
        loginUseCase: LoginUseCase,
        validateEmailUseCase: ValidateEmailUseCase,
        passwordUseCase: ValidatePasswordUseCase,
        saveValueToLocalStorageUseCase: SaveValueToLocalStorageUseCase,

        ): LoginUseCaseWrapper {
        return LoginUseCaseWrapper(
            loginUseCase = loginUseCase,
            validateEmailUseCase = validateEmailUseCase,
            validatePasswordUseCase = passwordUseCase,
            saveValueToLocalStorageUseCase = saveValueToLocalStorageUseCase
        )
    }
}
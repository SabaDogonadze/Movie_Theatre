package com.example.feature.login.di


import com.example.core.domain.use_case.SaveValueToLocalStorageUseCase
import com.example.core.domain.use_case.ValidateEmailUseCase
import com.example.core.domain.use_case.ValidatePasswordUseCase
import com.example.feaute.login.domain.use_case.LoginUseCase
import com.example.feaute.login.domain.use_case.LoginUseCaseWrapper
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
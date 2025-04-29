package com.example.feature.login.di


import com.example.feature.login.data.repository.ForgotPasswordRepositoryImpl
import com.example.feature.login.data.repository.LoginRepositoryImpl
import com.example.feaute.login.domain.repository.ForgotPasswordRepository
import com.example.feaute.login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindForgotPasswordRepository(impl: ForgotPasswordRepositoryImpl): ForgotPasswordRepository
}
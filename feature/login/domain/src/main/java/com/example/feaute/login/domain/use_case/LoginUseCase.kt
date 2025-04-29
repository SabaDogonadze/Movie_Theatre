package com.example.feaute.login.domain.use_case

import com.example.core.domain.util.Resource
import com.example.feaute.login.domain.repository.LoginRepository
import com.example.feaute.login.domain.util.LoginError
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): Resource<Unit, LoginError> {
        return loginRepository.login(email, password)
    }
}
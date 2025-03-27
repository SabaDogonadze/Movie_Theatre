package com.example.movietheatre.feature_login.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_login.domain.repository.LoginRepository
import com.example.movietheatre.feature_login.domain.util.LoginError
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String): Resource<Unit, LoginError> {
        return loginRepository.login(email, password)
    }
}
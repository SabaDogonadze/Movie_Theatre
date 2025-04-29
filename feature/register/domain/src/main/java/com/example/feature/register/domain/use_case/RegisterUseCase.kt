package com.example.feature.register.domain.use_case

import com.example.core.domain.util.Resource
import com.example.feature.register.domain.repository.RegisterRepository
import com.example.feature.register.domain.util.RegisterError
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): Resource<Unit, RegisterError> {
        return repository.register(email, password)
    }
}
package com.example.movietheatre.feature_register.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.RegisterError
import com.example.movietheatre.feature_register.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository) {
    suspend operator fun invoke(email: String, password: String): Resource<Unit, RegisterError> {
        return repository.register(email, password)
    }
}
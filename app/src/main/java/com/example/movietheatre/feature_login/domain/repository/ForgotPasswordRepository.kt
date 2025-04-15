package com.example.movietheatre.feature_login.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_login.domain.util.ForgotPasswordError

interface ForgotPasswordRepository {
    suspend fun sendPasswordResetEmail(email: String): Resource<Unit, ForgotPasswordError>

}
package com.example.feaute.login.domain.repository

import com.example.core.domain.util.Resource
import com.example.feaute.login.domain.util.ForgotPasswordError

interface ForgotPasswordRepository {
    suspend fun sendPasswordResetEmail(email: String): Resource<Unit, ForgotPasswordError>

}
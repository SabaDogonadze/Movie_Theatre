package com.example.movietheatre.feature_login.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_login.domain.util.LoginError

interface LoginRepository {
    suspend fun login(email: String, password: String): Resource<Unit, LoginError>

}
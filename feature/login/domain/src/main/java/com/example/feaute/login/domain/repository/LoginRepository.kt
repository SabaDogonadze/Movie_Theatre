package com.example.feaute.login.domain.repository

import com.example.core.domain.util.Resource
import com.example.feaute.login.domain.util.LoginError

interface LoginRepository {
    suspend fun login(email: String, password: String): Resource<Unit, LoginError>

}
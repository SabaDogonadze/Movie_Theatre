package com.example.feature.register.domain.repository

import com.example.core.domain.util.Resource
import com.example.feature.register.domain.util.RegisterError

interface RegisterRepository {

    suspend fun register(email:String,password:String): Resource<Unit, RegisterError>
}
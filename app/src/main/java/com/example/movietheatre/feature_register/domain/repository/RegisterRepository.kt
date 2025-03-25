package com.example.movietheatre.feature_register.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.RegisterError

interface RegisterRepository {

    suspend fun register(email:String,password:String):Resource<Unit,RegisterError>
}
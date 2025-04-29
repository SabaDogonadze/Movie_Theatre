package com.example.feature.profile.domain.repository

import com.example.core.domain.util.Resource
import com.example.feature.profile.domain.util.LogOutError

interface SignOutRepository {

    suspend fun signOut(): Resource<Unit, LogOutError>
}
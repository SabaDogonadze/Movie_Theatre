package com.example.feature.profile.data.remote.repository

import com.example.core.domain.util.Resource
import com.example.feature.profile.domain.repository.SignOutRepository
import com.example.feature.profile.domain.util.LogOutError
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignOutRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : SignOutRepository {

    override suspend fun signOut(): Resource<Unit, LogOutError> {
        return try {
            firebaseAuth.signOut()
            Resource.Success(Unit)
        } catch (throwable: Throwable) {
            Resource.Error(LogOutError.SOMETHING_WENT_WRONG)
        }
    }
}
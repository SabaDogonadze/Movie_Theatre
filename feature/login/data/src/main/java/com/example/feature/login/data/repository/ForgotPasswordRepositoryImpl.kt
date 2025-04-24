package com.example.feature.login.data.repository

import com.example.core.domain.util.Resource
import com.example.feaute.login.domain.repository.ForgotPasswordRepository
import com.example.feaute.login.domain.util.ForgotPasswordError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForgotPasswordRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : ForgotPasswordRepository {
    override suspend fun sendPasswordResetEmail(email: String): Resource<Unit, ForgotPasswordError> =
        withContext(Dispatchers.IO) {
            try {
                firebaseAuth.sendPasswordResetEmail(email).await()
                Resource.Success(Unit)
            } catch (e: Exception) {
                when (e) {
                    is FirebaseAuthInvalidUserException -> Resource.Error(
                        ForgotPasswordError.USER_NOT_FOUND
                    )

                    is FirebaseTooManyRequestsException -> Resource.Error(
                        ForgotPasswordError.TOO_MANY_REQUEST
                    )

                    is FirebaseNetworkException -> Resource.Error(
                        ForgotPasswordError.NETWORK_ERROR
                    )

                    else -> Resource.Error(
                        ForgotPasswordError.UNKNOWN_ERROR
                    )
                }
            }
        }
}
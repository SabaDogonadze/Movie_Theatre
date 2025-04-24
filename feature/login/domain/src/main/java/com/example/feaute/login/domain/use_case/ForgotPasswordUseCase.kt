package com.example.feaute.login.domain.use_case

import com.example.core.domain.util.Resource
import com.example.feaute.login.domain.repository.ForgotPasswordRepository
import com.example.feaute.login.domain.util.ForgotPasswordError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val forgotPasswordRepository: ForgotPasswordRepository,
) {
    suspend operator fun invoke(email: String): Resource<Unit, ForgotPasswordError> {
        return try {
            forgotPasswordRepository.sendPasswordResetEmail(email)
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

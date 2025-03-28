package com.example.movietheatre.feature_login.data.repository

import android.util.Log
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_login.domain.repository.LoginRepository
import com.example.movietheatre.feature_login.domain.util.LoginError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    LoginRepository {

    override suspend fun login(email: String, password: String): Resource<Unit, LoginError> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user?.let {
                Resource.Success(Unit)
            } ?: Resource.Error(LoginError.UnknownError)
        } catch (exception: Exception) {
            Log.d("exception", exception.toString())
            val error = when (exception) {
                is FirebaseAuthInvalidUserException -> LoginError.UserNotFound
                is FirebaseNetworkException -> LoginError.NoInternetError
                is FirebaseAuthInvalidCredentialsException -> LoginError.InvalidCredentials
                else -> LoginError.UnknownError
            }
            Resource.Error(error)
        }
    }
}
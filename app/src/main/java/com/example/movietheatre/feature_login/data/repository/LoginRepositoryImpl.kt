package com.example.movietheatre.feature_login.data.repository

import android.util.Log
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_login.domain.repository.LoginRepository
import com.example.movietheatre.feature_login.domain.util.LoginError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    LoginRepository {
    override suspend fun login(email: String, password: String): Resource<Unit, LoginError> {
        return suspendCoroutine { cont ->
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val firebaseUser = authResult.user

                    firebaseUser?.let {
                        cont.resume(Resource.Success(Unit))
                    } ?: cont.resume(Resource.Error(LoginError.UnknownError))

                }
                .addOnFailureListener { exception ->
                    Log.d("exception", exception.toString())
                    val error = when (exception) {
                        is FirebaseAuthInvalidUserException -> LoginError.UserNotFound
                        is FirebaseNetworkException -> LoginError.NoInternetError
                        is FirebaseAuthInvalidCredentialsException -> LoginError.InvalidCredentials
                        else -> LoginError.UnknownError
                    }
                    cont.resume(Resource.Error(error))
                }
        }
    }
}
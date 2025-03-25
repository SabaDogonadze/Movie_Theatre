package com.example.movietheatre.feature_register.data.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.RegisterError
import com.example.movietheatre.feature_register.domain.repository.RegisterRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RegisterRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    RegisterRepository {
    override suspend fun register(email: String, password: String): Resource<Unit, RegisterError> {
        return suspendCoroutine { cont ->
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val firebaseUser = authResult.user
                    if (firebaseUser != null) {
                        cont.resume(Resource.Success(Unit))
                    } else {
                        cont.resume(Resource.Error(RegisterError.UnknownError))
                    }
                }
                .addOnFailureListener { exception ->
                    val error = when (exception) {
                        is FirebaseAuthUserCollisionException -> RegisterError.EmailAlreadyExists
                        is FirebaseNetworkException -> RegisterError.NoInternetError
                        else -> RegisterError.UnknownError
                    }
                    cont.resume(Resource.Error(error))
                }
        }
    }
}
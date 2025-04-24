package com.example.feature.register.data.repository


import com.example.core.domain.util.Resource
import com.example.feature.register.domain.repository.RegisterRepository
import com.example.feature.register.domain.util.RegisterError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    RegisterRepository {
    override suspend fun register(
        email: String,
        password: String,
    ): Resource<Unit, RegisterError> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (authResult.user != null) {
                Resource.Success(Unit)
            } else {
                Resource.Error(RegisterError.UnknownError)
            }
        } catch (exception: Exception) {
            val error = when (exception) {
                is FirebaseAuthUserCollisionException -> RegisterError.EmailAlreadyExists
                is FirebaseNetworkException -> RegisterError.NoInternetError
                else -> RegisterError.UnknownError
            }
            Resource.Error(error)
        }
    }
}
package com.example.movietheatre.feature_profile.domain.repository

import android.net.Uri
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun uploadProfileImage(uid: String, uri: Uri): Resource<Uri, NetworkError>
    fun observeProfileImage(uid: String): Flow<Resource<Uri, NetworkError>>
}
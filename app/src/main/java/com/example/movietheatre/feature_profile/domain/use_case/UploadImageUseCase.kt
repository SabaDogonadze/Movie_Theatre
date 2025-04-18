package com.example.movietheatre.feature_profile.domain.use_case

import android.net.Uri
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.repository.ProfileRepository
import javax.inject.Inject

class UploadProfileImage @Inject constructor(
    private val repository: ProfileRepository,
) {
    suspend operator fun invoke(uid: String, uri: Uri): Resource<Uri, NetworkError> =
        repository.uploadProfileImage(uid, uri)
}

package com.example.movietheatre.feature_profile.domain.use_case

import android.net.Uri
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetProfileImage @Inject constructor(
    private val repository: ProfileRepository,
) {
    operator fun invoke(uid: String): Flow<Resource<Uri, NetworkError>> =
        repository.observeProfileImage(uid)
}
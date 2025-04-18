package com.example.movietheatre.feature_profile.presentation.profile

import android.net.Uri
import com.example.movietheatre.core.domain.util.error.NetworkError


data class ProfileState(
    val currentImage: Uri? = null,
    val isLoading: Boolean = false,
    val error: NetworkError? = null
)
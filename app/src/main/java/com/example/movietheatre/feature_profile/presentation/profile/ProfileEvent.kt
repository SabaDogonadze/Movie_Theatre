package com.example.movietheatre.feature_profile.presentation.profile

import android.net.Uri

sealed class ProfileEvent {
    object LoadImage : ProfileEvent()
    data class SelectImage(val uri: Uri) : ProfileEvent()
}
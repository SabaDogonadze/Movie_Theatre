package com.example.movietheatre.feature_profile.presentation.profile

sealed interface ProfileEffect {
    data class ShowToast(val message: String) : ProfileEffect
}
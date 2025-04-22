package com.example.movietheatre.feature_profile.presentation.event

sealed interface ProfileSideEffect {

    data class ShowError(val message: Int) : ProfileSideEffect
}
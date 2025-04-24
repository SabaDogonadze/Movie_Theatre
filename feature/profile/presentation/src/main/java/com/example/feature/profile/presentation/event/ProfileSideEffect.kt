package com.example.feature.profile.presentation.event

sealed interface ProfileSideEffect {

    data class ShowError(val message: Int) :
        com.example.feature.profile.presentation.event.ProfileSideEffect
}
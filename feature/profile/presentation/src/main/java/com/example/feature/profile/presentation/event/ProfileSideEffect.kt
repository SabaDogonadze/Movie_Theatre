package com.example.feature.profile.presentation.event

sealed interface ProfileSideEffect {

    data class ShowError(val message: Int) :
        ProfileSideEffect

    data object SignOut : ProfileSideEffect

    data class ApplyLanguage(val languageCode: String) : ProfileSideEffect

}
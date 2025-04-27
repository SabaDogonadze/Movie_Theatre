package com.example.feature.profile.presentation.event

sealed interface ProfileEvent {

    data object GetCoins : ProfileEvent
    data object SignOut : ProfileEvent

}
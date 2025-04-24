package com.example.feature.profile.presentation.event

sealed interface  ProfileEvent {

    data object GetCoins: com.example.feature.profile.presentation.event.ProfileEvent
}
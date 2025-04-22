package com.example.movietheatre.feature_profile.presentation.event

sealed interface  ProfileEvent {

    data object GetCoins:ProfileEvent
}
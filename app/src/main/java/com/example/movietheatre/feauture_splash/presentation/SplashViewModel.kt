package com.example.movietheatre.feauture_splash.presentation

import androidx.lifecycle.ViewModel
import com.example.movietheatre.core.domain.use_case.GetValueFromLocalStorageUseCase
import com.example.movietheatre.core.domain.preference_key.PreferenceKeys.REMEMBER_ME
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    getValueFromLocalStorageUseCase: GetValueFromLocalStorageUseCase,
    firebaseAuth: FirebaseAuth,
) : ViewModel() {

    val currentUser = firebaseAuth.currentUser

    val rememberMeFlow = getValueFromLocalStorageUseCase(REMEMBER_ME, false)
}
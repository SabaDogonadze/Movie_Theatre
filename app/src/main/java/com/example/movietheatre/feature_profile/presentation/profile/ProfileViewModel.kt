package com.example.movietheatre.feature_profile.presentation.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.use_case.GetProfileImage
import com.example.movietheatre.feature_profile.domain.use_case.UploadProfileImage
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val uploadUseCase: UploadProfileImage,
    private val getUseCase: GetProfileImage,
    private val auth: FirebaseAuth,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _effect = Channel<ProfileEffect>(Channel.BUFFERED)
    val effect: Flow<ProfileEffect> = _effect.receiveAsFlow()

    init {
        send(ProfileEvent.LoadImage)
    }

    fun send(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LoadImage -> loadImage()
            is ProfileEvent.SelectImage -> selectAndUpload(event.uri)
        }
    }

    private fun loadImage() {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            getUseCase(uid).collect { res ->
                when (res) {
                    is Resource.Success -> _state.update { it.copy(currentImage = res.data) }
                    is Resource.Error -> if (res.error !is NetworkError.EmptyResponse)
                        _state.update { it.copy(error = res.error) }
                }
            }
        }
    }

    private fun selectAndUpload(uri: Uri) {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            // show preview immediately
            _state.update { it.copy(currentImage = uri, isLoading = true, error = null) }

            when (val res = uploadUseCase(uid, uri)) {
                is Resource.Success -> _state.update { it.copy(isLoading = false) }
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.error) }
                    _effect.send(ProfileEffect.ShowToast("Upload failed: ${res.error}"))
                }
            }
        }
    }
}
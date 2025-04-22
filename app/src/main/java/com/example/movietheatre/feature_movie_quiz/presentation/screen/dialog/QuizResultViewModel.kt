package com.example.movietheatre.feature_movie_quiz.presentation.screen.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.use_case.UpdateCoinUseCase
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.presentation.extension.asStringResource
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizResultEvent
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizResultSideEffect
import com.example.movietheatre.feature_movie_quiz.presentation.state.QuizResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizResultViewModel @Inject constructor(
    private val updateCoinUseCase: UpdateCoinUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizResultUiState())
    val uiState: StateFlow<QuizResultUiState> = _uiState

    private val _sideEffects = MutableSharedFlow<QuizResultSideEffect>()
    val sideEffects = _sideEffects.asSharedFlow()

    fun onEvent(event: QuizResultEvent) {
        when (event) {
            is QuizResultEvent.UpdateUserCoin -> updateCoinForUser(event.coins)
        }
    }

    private fun updateCoinForUser(coins: Int) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = updateCoinUseCase(coins)) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffects.emit(QuizResultSideEffect.ShowError(result.error.asStringResource()))
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false) }

                }
            }
        }
    }
}
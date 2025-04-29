package com.example.feature.movie_quiz.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.util.Resource
import com.example.feature.movie_quiz.domain.usecase.GetQuizCategoriesUseCase
import com.example.feature.movie_quiz.domain.usecase.GetQuizCompletedUseCase
import com.example.feature.movie_quiz.domain.usecase.MarkQuizCompletedUseCase
import com.example.feature.movie_quiz.presentation.event.QuizCategoryEvent
import com.example.feature.movie_quiz.presentation.event.QuizCategorySideEffect
import com.example.feature.movie_quiz.presentation.mapper.toPresenter
import com.example.feature.movie_quiz.presentation.model.QuizCategoryPresenter
import com.example.feature.movie_quiz.presentation.model.QuizCompletedPresenter
import com.example.feature.movie_quiz.presentation.state.QuizCategoryState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizCategoryViewModel @Inject constructor(
    private val getQuizCategoriesUseCase: GetQuizCategoriesUseCase,
    private val getQuizCompletedUseCase: GetQuizCompletedUseCase,
    private val markQuizCompletedUseCase: MarkQuizCompletedUseCase,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private val currentUserId: String
        get() = firebaseAuth.currentUser?.uid!!

    private val _state = MutableStateFlow(QuizCategoryState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<QuizCategorySideEffect>()
    val uiEvents = _uiEvents.asSharedFlow()

    init {
        loadData()
    }

    fun event(event: QuizCategoryEvent) {
        when (event) {
            is QuizCategoryEvent.LoadCategories -> loadData()
            is QuizCategoryEvent.SelectCategory -> selectCategory(event.categoryId)
            is QuizCategoryEvent.RefreshCompletedQuizzes -> loadCompletedQuizzes()
        }
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            val categoriesDeferred = async(Dispatchers.IO) { loadCategoriesAsync() }
            val completedQuizzesDeferred = async(Dispatchers.IO) { loadCompletedQuizzesAsync() }

            try {
                val categories = categoriesDeferred.await()
                val completedQuizzes = completedQuizzesDeferred.await()

                updateStateWithResults(categories, completedQuizzes)
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                _uiEvents.emit(QuizCategorySideEffect.ShowError("Failed to load quiz data"))
            }
        }
    }

    private suspend fun loadCategoriesAsync(): List<QuizCategoryPresenter> {
        return try {
            getQuizCategoriesUseCase().map { it.toPresenter() }
        } catch (e: Exception) {
            _uiEvents.emit(QuizCategorySideEffect.ShowError("Failed to load categories"))
            emptyList()
        }
    }

    private suspend fun loadCompletedQuizzesAsync(): List<QuizCompletedPresenter> {
        return try {
            when (val result = getQuizCompletedUseCase(currentUserId)) {
                is Resource.Success -> result.data.map { it.toPresenter() }
                is Resource.Error -> {
                    _uiEvents.emit(QuizCategorySideEffect.ShowError("Failed to load completed quizzes"))
                    emptyList()
                }
            }
        } catch (e: Exception) {
            _uiEvents.emit(QuizCategorySideEffect.ShowError("Error: ${e.message}"))
            emptyList()
        }
    }

    private fun updateStateWithResults(
        categories: List<QuizCategoryPresenter>,
        completedQuizzes: List<QuizCompletedPresenter>,
    ) {
        val completedQuizIds = completedQuizzes.map { it.quizId }

        val updatedCategories = categories.map { category ->
            category.copy(isCompleted = category.id in completedQuizIds)
        }

        _state.update {
            it.copy(
                isLoading = false,
                categories = updatedCategories,
                completedQuizzes = completedQuizzes,
            )
        }
    }

    private fun loadCompletedQuizzes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val completedQuizzes = loadCompletedQuizzesAsync()
                val updatedCategories = _state.value.categories.map { category ->
                    category.copy(isCompleted = category.id in completedQuizzes.map { it.quizId })
                }

                _state.update {
                    it.copy(
                        completedQuizzes = completedQuizzes,
                        categories = updatedCategories
                    )
                }
            } catch (e: Exception) {
                _uiEvents.emit(QuizCategorySideEffect.ShowError("Failed to refresh completed quizzes"))
            }
        }
    }

    private fun selectCategory(categoryId: Int) {
        val category = _state.value.categories.find { it.id == categoryId }

        viewModelScope.launch(Dispatchers.IO) {
            if (category == null) {
                _uiEvents.emit(QuizCategorySideEffect.ShowError("Quiz category not found"))
                return@launch
            }

            if (category.isCompleted) {
                _uiEvents.emit(QuizCategorySideEffect.ShowError("This quiz has already been completed"))
            } else {
                try {
                    val result = markQuizCompletedUseCase(currentUserId, categoryId)

                    val updatedCategories = _state.value.categories.map {
                        if (it.id == categoryId) it.copy(isCompleted = true) else it
                    }

                    _state.update {
                        it.copy(categories = updatedCategories)
                    }

                    _uiEvents.emit(
                        QuizCategorySideEffect.NavigateToQuiz(
                            categoryId = categoryId,
                            coins = category.rewardCoins
                        )
                    )
                } catch (e: Exception) {
                    _uiEvents.emit(QuizCategorySideEffect.ShowError("Failed to update quiz status: ${e.message}"))
                }
            }
        }
    }
}
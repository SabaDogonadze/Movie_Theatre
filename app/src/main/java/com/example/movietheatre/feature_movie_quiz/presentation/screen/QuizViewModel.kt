package com.example.movietheatre.feature_movie_quiz.presentation.screen

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_movie_quiz.domain.usecase.CompleteQuizUseCase
import com.example.movietheatre.feature_movie_quiz.domain.usecase.GetQuizQuestionsUseCase
import com.example.movietheatre.feature_movie_quiz.domain.usecase.SubmitAnswerUseCase
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizEvent
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizSideEffect
import com.example.movietheatre.feature_movie_quiz.presentation.mapper.toPresenter
import com.example.movietheatre.feature_movie_quiz.presentation.state.QuizState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val QUIZ_TIMER_SECONDS = 45
private const val QUIZ_TIMER_MILLIS = QUIZ_TIMER_SECONDS * 1000L
private const val TIMER_INTERVAL = 1000L

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizQuestionsUseCase: GetQuizQuestionsUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase,
    private val completeQuizUseCase: CompleteQuizUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state: StateFlow<QuizState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<QuizSideEffect>()
    val effect: SharedFlow<QuizSideEffect> = _effect.asSharedFlow()

    private var countDownTimer: CountDownTimer? = null

    // Track dialog visibility to prevent multiple dialogs
    private var isDialogShowing = false

    // Store the remaining time
    private var currentTimeRemaining = QUIZ_TIMER_SECONDS

    // Flag to track if the timer has been started
    private var timerStarted = false

    fun onEvent(event: QuizEvent) {
        when (event) {
            is QuizEvent.LoadQuestions -> loadQuestions(event.categoryId)
            is QuizEvent.SelectAnswer -> selectAnswer(event.answerId)
            is QuizEvent.NextQuestion -> moveToNextQuestion()
            is QuizEvent.TimerTick -> { /* Handled by CountDownTimer */ }
            is QuizEvent.QuizComplete -> completeQuiz()
            is QuizEvent.TimeUp -> handleTimeUp()
            is QuizEvent.DialogDismissed -> isDialogShowing = false
        }
    }

    private fun loadQuestions(categoryId: Int) {
        _state.update { it.copy(isLoading = true, quizCategoryId = categoryId) }

        viewModelScope.launch {
            when (val result = getQuizQuestionsUseCase(categoryId)) {
                is Resource.Success -> {
                    val questionPresenters = result.data.map { it.toPresenter() }
                    if (questionPresenters.isNotEmpty()) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                questions = questionPresenters,
                                currentQuestion = questionPresenters.first(),
                                totalQuestions = questionPresenters.size,
                                error = null,
                                timeRemaining = QUIZ_TIMER_SECONDS
                            )
                        }
                        currentTimeRemaining = QUIZ_TIMER_SECONDS
                        // Start the timer only once when questions are first loaded
                        if (!timerStarted) {
                            startTimer()
                            timerStarted = true
                        }
                    } else {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = "No questions found for this quiz"
                            )
                        }
                        showMessage("No questions found for this quiz")
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to load questions: ${result.error}"
                        )
                    }
                    showMessage("Failed to load questions")
                }
            }
        }
    }

    private fun selectAnswer(answerId: String) {
        if (_state.value.hasAnswered) return // Prevent changing answer after submission

        val currentQuestion = _state.value.currentQuestion ?: return
        val questionId = currentQuestion.id
        val isCorrect = answerId == currentQuestion.correctOptionId

        // Save the current timer value
        currentTimeRemaining = _state.value.timeRemaining

        _state.update {
            it.copy(
                selectedAnswerId = answerId,
                hasAnswered = true,
                isCorrectAnswer = isCorrect
            )
        }

        viewModelScope.launch {
            try {
                // Submit the answer using the use case
                val submissionSuccessful = submitAnswerUseCase(questionId, answerId)

                if (submissionSuccessful) {
                    if (isCorrect) {
                        _state.update {
                            it.copy(correctAnswersCount = it.correctAnswersCount + 1)
                        }
                    } else {
                        // Wrong answer - show feedback and end quiz
                        showWrongAnswerDialog()
                        countDownTimer?.cancel()
                    }
                } else {
                    showMessage("Failed to submit answer")
                }
            } catch (e: Exception) {
                showMessage("Error: ${e.message}")
            }
        }
    }

    private fun moveToNextQuestion() {
        val currentState = _state.value
        val nextIndex = currentState.currentQuestionIndex + 1

        if (nextIndex < currentState.questions.size) {
            // Don't reset timer - maintain the current time
            _state.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    currentQuestion = it.questions[nextIndex],
                    selectedAnswerId = null,
                    hasAnswered = false,
                    isCorrectAnswer = null
                    // Not updating timeRemaining here - let the timer continue
                )
            }

            // No need to restart timer - it's already running continuously
        } else {
            // All questions completed
            completeQuiz()
        }
    }

    private fun startTimer() {
        countDownTimer?.cancel()

        // Start with the full time for the first time
        val millisRemaining = QUIZ_TIMER_MILLIS

        countDownTimer = object : CountDownTimer(millisRemaining, TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                _state.update {
                    it.copy(timeRemaining = secondsRemaining)
                }
                currentTimeRemaining = secondsRemaining
            }

            override fun onFinish() {
                _state.update { it.copy(timeRemaining = 0) }
                currentTimeRemaining = 0
                handleTimeUp()
            }
        }.start()
    }

    private fun handleTimeUp() {
        if (!isDialogShowing) {
            viewModelScope.launch {
                showTimeUpDialog()
                countDownTimer?.cancel()
            }
        }
    }

    private fun completeQuiz() {
        if (!isDialogShowing) {
            viewModelScope.launch {
                // Get the current user ID from Firebase Auth
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                val quizId = _state.value.quizCategoryId

                completeQuizUseCase(userId, quizId)

                // Navigate to results
                showResultsDialog()
            }
        }
    }

    // Helper methods to control dialog visibility
    private suspend fun showMessage(message: String) {
        _effect.emit(QuizSideEffect.ShowMessage(message))
    }

    private suspend fun showWrongAnswerDialog() {
        if (!isDialogShowing) {
            isDialogShowing = true
            _effect.emit(QuizSideEffect.ShowWrongAnswerDialog)
        }
    }

    private suspend fun showTimeUpDialog() {
        if (!isDialogShowing) {
            isDialogShowing = true
            _effect.emit(QuizSideEffect.ShowTimeUpDialog)
        }
    }

    private suspend fun showResultsDialog() {
        if (!isDialogShowing) {
            isDialogShowing = true
            _effect.emit(QuizSideEffect.NavigateToResults)
        }
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }
}
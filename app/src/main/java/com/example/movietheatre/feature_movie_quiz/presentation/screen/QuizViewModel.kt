package com.example.movietheatre.feature_movie_quiz.presentation.screen

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.feature_movie_quiz.domain.usecase.CompleteQuizUseCase
import com.example.movietheatre.feature_movie_quiz.domain.usecase.GetQuizQuestionsUseCase
import com.example.movietheatre.feature_movie_quiz.domain.usecase.SubmitAnswerUseCase
import com.example.movietheatre.feature_movie_quiz.presentation.event.QuizEvent
import com.example.movietheatre.feature_movie_quiz.presentation.mapper.toPresenter
import com.example.movietheatre.feature_movie_quiz.presentation.state.QuizState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val completeQuizUseCase: CompleteQuizUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state: StateFlow<QuizState> = _state.asStateFlow()

    private val _showWrongAnswerDialog = MutableStateFlow(false)
    val showWrongAnswerDialog: StateFlow<Boolean> = _showWrongAnswerDialog

    private val _showTimeUpDialog = MutableStateFlow(false)
    val showTimeUpDialog: StateFlow<Boolean> = _showTimeUpDialog

    private val _showResultsDialog = MutableStateFlow(false)
    val showResultsDialog: StateFlow<Boolean> = _showResultsDialog

    private var countDownTimer: CountDownTimer? = null
    private var currentTimeRemaining = QUIZ_TIMER_SECONDS
    private var timerStarted = false

    fun onEvent(event: QuizEvent) {
        when (event) {
            is QuizEvent.LoadQuestions -> loadQuestions(event.categoryId)
            is QuizEvent.SelectAnswer -> selectAnswer(event.answerId)
            is QuizEvent.NextQuestion -> moveToNextQuestion()
            is QuizEvent.QuizComplete -> completeQuiz()
            is QuizEvent.TimeUp -> handleTimeUp()
            is QuizEvent.DialogDismissed -> {
                _showWrongAnswerDialog.value = false
                _showTimeUpDialog.value = false
                _showResultsDialog.value = false
            }
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
                                timeRemaining = QUIZ_TIMER_SECONDS
                            )
                        }
                        currentTimeRemaining = QUIZ_TIMER_SECONDS
                        if (!timerStarted) {
                            startTimer()
                            timerStarted = true
                        }
                    } else {
                        _state.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }


    private fun selectAnswer(answerId: String) {
        if (_state.value.hasAnswered) {
            return
        }

        val currentQuestion = _state.value.currentQuestion ?: return
        val isCorrect = answerId == currentQuestion.correctOptionId

        _state.update {
            it.copy(
                selectedAnswerId = answerId,
                hasAnswered = true,
                isCorrectAnswer = isCorrect
            )
        }
        if (isCorrect) {
            _state.update {
                it.copy(correctAnswersCount = it.correctAnswersCount + 1)
            }
        } else {
            countDownTimer?.cancel()
            _showWrongAnswerDialog.value = true
        }
    }

    private fun moveToNextQuestion() {
        val currentState = _state.value
        val nextIndex = currentState.currentQuestionIndex + 1

        if (nextIndex < currentState.questions.size) {
            _state.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    currentQuestion = it.questions[nextIndex],
                    selectedAnswerId = null,
                    hasAnswered = false,
                    isCorrectAnswer = null
                )
            }
        } else {
            completeQuiz()
        }
    }

    private fun startTimer() {
        countDownTimer?.cancel()

        val remainingMillis = currentTimeRemaining * 1000L

        countDownTimer = object : CountDownTimer(remainingMillis, TIMER_INTERVAL) {
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
        _showTimeUpDialog.value = true
        countDownTimer?.cancel()
        completeQuiz()
    }

    private fun completeQuiz() {
        viewModelScope.launch {
            countDownTimer?.cancel()
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val quizId = _state.value.quizCategoryId

            completeQuizUseCase(userId, quizId)
            _showResultsDialog.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }
}

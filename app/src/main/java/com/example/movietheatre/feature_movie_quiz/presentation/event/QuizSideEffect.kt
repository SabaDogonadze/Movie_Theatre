package com.example.movietheatre.feature_movie_quiz.presentation.event

sealed class QuizSideEffect {
    data object NavigateToResults : QuizSideEffect()
    data class ShowMessage(val message: String) : QuizSideEffect()
    data object ShowTimeUpDialog : QuizSideEffect()
    data object ShowWrongAnswerDialog : QuizSideEffect()
}

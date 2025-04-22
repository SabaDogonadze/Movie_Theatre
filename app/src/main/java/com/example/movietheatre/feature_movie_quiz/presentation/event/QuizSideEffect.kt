package com.example.movietheatre.feature_movie_quiz.presentation.event

sealed class QuizSideEffect {
    data object NavigateToResults : QuizSideEffect()
    data object ShowTimeUpDialog : QuizSideEffect()
    data object ShowWrongAnswerDialog : QuizSideEffect()
    data object NavigateToCategories : QuizSideEffect()
}

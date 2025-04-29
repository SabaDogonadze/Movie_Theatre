package com.example.feature.movie_quiz.presentation.event

sealed class QuizEvent {
    data class LoadQuestions(val categoryId: Int) : QuizEvent()
    data class SelectAnswer(val answerId: String) : QuizEvent()
    data object NextQuestion : QuizEvent()
    data object QuizComplete : QuizEvent()
    data object TimeUp : QuizEvent()
    data object DialogDismissed : QuizEvent()
    data object WarningDialogDismissed : QuizEvent()
}
package com.example.feature.movie_quiz.presentation.event

sealed class QuizEvent {
    data class LoadQuestions(val categoryId: Int) : com.example.feature.movie_quiz.presentation.event.QuizEvent()
    data class SelectAnswer(val answerId: String) : com.example.feature.movie_quiz.presentation.event.QuizEvent()
    data object NextQuestion : com.example.feature.movie_quiz.presentation.event.QuizEvent()
    data object QuizComplete : com.example.feature.movie_quiz.presentation.event.QuizEvent()
    data object TimeUp : com.example.feature.movie_quiz.presentation.event.QuizEvent()
    data object DialogDismissed : com.example.feature.movie_quiz.presentation.event.QuizEvent()
}
package com.example.movietheatre.feature_movie_quiz.presentation.mapper

import com.example.movietheatre.feature_movie_quiz.domain.model.QuizResult
import com.example.movietheatre.feature_movie_quiz.presentation.model.QuizResultPresenter

fun QuizResult.toPresenter():QuizResultPresenter{
    return QuizResultPresenter(totalQuestions = totalQuestions, correctAnswers = correctAnswers, score = score)
}
package com.example.movietheatre.feature_movie_quiz.presentation.mapper

import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCompleted
import com.example.movietheatre.feature_movie_quiz.presentation.model.QuizCompletedPresenter

fun QuizCompleted.toPresenter():QuizCompletedPresenter{
    return QuizCompletedPresenter(userUid = userUid, quizId = quizId, score = score)
}
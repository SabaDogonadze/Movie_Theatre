package com.example.movietheatre.feature_movie_quiz.data.remote.mapper

import com.example.movietheatre.feature_movie_quiz.data.remote.model.QuizCompletedDto
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCompleted

fun QuizCompletedDto.toDomain(): QuizCompleted {
    return QuizCompleted(userUid = userUid, quizId = quizId, score = score?:0)
}
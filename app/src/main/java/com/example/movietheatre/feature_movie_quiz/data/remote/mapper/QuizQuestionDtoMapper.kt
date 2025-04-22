package com.example.movietheatre.feature_movie_quiz.data.remote.mapper

import com.example.movietheatre.feature_movie_quiz.data.remote.model.QuizQuestionDto
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizQuestion

fun QuizQuestionDto.toDomain(): QuizQuestion {
    return QuizQuestion(
        id = id,
        question = question,
        imageUrl = imageUrl,
        options = options.map { it.toDomain() },
        correctOptionId = correctOptionId
    )
}
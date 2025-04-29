package com.example.feature.movie_quiz.data.remote.mapper

import com.example.feature.movie_quiz.data.remote.model.QuizQuestionDto
import com.example.feature.movie_quiz.domain.model.QuizQuestion

fun QuizQuestionDto.toDomain(): QuizQuestion {
    return QuizQuestion(
        id = id,
        question = question,
        imageUrl = imageUrl,
        options = options.map { it.toDomain() },
        correctOptionId = correctOptionId
    )
}
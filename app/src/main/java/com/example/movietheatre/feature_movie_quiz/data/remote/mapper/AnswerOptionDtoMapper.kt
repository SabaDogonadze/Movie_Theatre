package com.example.movietheatre.feature_movie_quiz.data.remote.mapper

import com.example.movietheatre.feature_movie_quiz.data.remote.model.AnswerOptionDto
import com.example.movietheatre.feature_movie_quiz.domain.model.AnswerOption

fun AnswerOptionDto.toDomain(): AnswerOption {
    return AnswerOption(id =id, letter = letter, text = text)
}
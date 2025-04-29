package com.example.feature.movie_quiz.data.remote.mapper

import com.example.feature.movie_quiz.data.remote.model.AnswerOptionDto
import com.example.feature.movie_quiz.domain.model.AnswerOption

fun AnswerOptionDto.toDomain(): AnswerOption {
    return AnswerOption(id = id, letter = letter, text = text)
}
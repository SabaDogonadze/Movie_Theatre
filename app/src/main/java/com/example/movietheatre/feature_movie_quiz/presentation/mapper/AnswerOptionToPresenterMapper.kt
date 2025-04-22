package com.example.movietheatre.feature_movie_quiz.presentation.mapper

import com.example.movietheatre.feature_movie_quiz.domain.model.AnswerOption
import com.example.movietheatre.feature_movie_quiz.presentation.model.AnswerOptionPresenter

fun AnswerOption.toPresenter():AnswerOptionPresenter{
    return AnswerOptionPresenter(id = id, letter = letter, text = text)
}
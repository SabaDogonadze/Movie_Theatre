package com.example.movietheatre.feature_movie_quiz.presentation.mapper

import com.example.movietheatre.feature_movie_quiz.domain.model.QuizQuestion
import com.example.movietheatre.feature_movie_quiz.presentation.model.AnswerOptionPresenter
import com.example.movietheatre.feature_movie_quiz.presentation.model.QuizQuestionPresenter

fun QuizQuestion.toPresenter():QuizQuestionPresenter{
    return QuizQuestionPresenter(
        id = id,
        question = question,
        imageUrl = imageUrl,
        options = this.options.map { opt ->
            AnswerOptionPresenter(
                id = opt.id,
                letter = opt.letter,
                text = opt.text
            )
        },
        correctOptionId = correctOptionId
    )
}


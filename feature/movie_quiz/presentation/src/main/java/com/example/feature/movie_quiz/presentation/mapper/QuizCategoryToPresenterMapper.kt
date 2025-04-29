package com.example.feature.movie_quiz.presentation.mapper

import com.example.feature.movie_quiz.domain.model.QuizCategory
import com.example.feature.movie_quiz.presentation.model.QuizCategoryPresenter

fun QuizCategory.toPresenter(): QuizCategoryPresenter {
    return QuizCategoryPresenter(
        id = id,
        title =title,
        imageUrl = imageUrl,
        questionCount = questionCount,
        rewardCoins = rewardCoins,
        difficulty = difficulty,
        isCompleted = false
    )
}
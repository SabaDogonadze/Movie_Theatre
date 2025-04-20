package com.example.movietheatre.feature_movie_quiz.data.remote.mapper

import com.example.movietheatre.feature_movie_quiz.data.remote.model.QuizCategoryDto
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCategory

fun QuizCategoryDto.toDomain():QuizCategory{
    return QuizCategory(
        id = id,
        title = title,
        imageUrl = imageUrl,
        questionCount = questionCount,
        rewardCoins = rewardCoins,
        difficulty = difficulty
    )
}
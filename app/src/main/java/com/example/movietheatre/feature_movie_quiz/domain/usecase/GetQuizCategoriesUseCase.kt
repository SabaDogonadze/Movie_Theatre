package com.example.movietheatre.feature_movie_quiz.domain.usecase

import com.example.movietheatre.feature_movie_quiz.domain.repository.QuizCategoryRepository
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCategory
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCompleted
import javax.inject.Inject

class GetQuizCategoriesUseCase @Inject constructor(
    private val repository: QuizCategoryRepository,
) {
    suspend operator fun invoke(): List<QuizCategory> {
        val resource: Resource<List<QuizCategory>, NetworkError> =
            repository.getQuizCategories()
        return when (resource) {
            is Resource.Success -> resource.data
            is Resource.Error -> emptyList()
        }
    }
}

/*
package com.example.movietheatre.feature_movie_quiz.domain.usecase

import com.example.movietheatre.feature_movie_quiz.domain.repository.QuizCategoryRepository
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_movie_quiz.domain.model.QuizCategory
import javax.inject.Inject

class GetQuizCategoriesUseCase @Inject constructor(
    private val repository: QuizCategoryRepository,
) {
    suspend operator fun invoke(): List<QuizCategory> {
        val resource: Resource<List<com.example.movietheatre.feature_movie_quiz.domain.model.QuizCategory>, NetworkError> =
            repository.getQuizCategories()
        return when (resource) {
            is Resource.Success -> resource.data.map { category ->
                QuizCategory(
                    id = category.id,
                    title = category.title,
                    imageUrl = category.imageUrl,
                    questionCount = category.questionCount,
                    rewardCoins = category.rewardCoins,
                    difficulty = category.difficulty
                )
            }
            is Resource.Error -> emptyList()
        }
    }
}
*/

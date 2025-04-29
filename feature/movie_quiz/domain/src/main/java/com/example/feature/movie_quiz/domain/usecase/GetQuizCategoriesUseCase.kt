package com.example.feature.movie_quiz.domain.usecase

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.movie_quiz.domain.model.QuizCategory
import com.example.feature.movie_quiz.domain.repository.QuizCategoryRepository
import javax.inject.Inject

class GetQuizCategoriesUseCase @Inject constructor(
    private val repository: QuizCategoryRepository,
) {
    suspend operator fun invoke(): List<QuizCategory> {
        val resource: com.example.core.domain.util.Resource<List<QuizCategory>, com.example.core.domain.util.error.NetworkError> =
            repository.getQuizCategories()
        return when (resource) {
            is com.example.core.domain.util.Resource.Success -> resource.data
            is com.example.core.domain.util.Resource.Error -> emptyList()
        }
    }
}
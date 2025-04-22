package com.example.movietheatre.feature_movie_quiz.data.remote.repository

import com.example.movietheatre.feature_movie_quiz.data.remote.service.QuizService
import com.example.movietheatre.feature_movie_quiz.domain.repository.SubmitAnswerRepository
import com.google.android.datatransport.runtime.logging.Logging.d
import retrofit2.HttpException
import javax.inject.Inject

class SubmitAnswerRepositoryImpl @Inject constructor(
    private val quizService: QuizService
) : SubmitAnswerRepository {

    override suspend fun submitAnswer(
        questionId: Int,
        selectedAnswerId: String
    ): Boolean {
        val response = quizService.getQuizQuestionsById(questionId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        val questions = response.body()
            ?: throw IllegalStateException("No question data returned for ID $questionId")

        val question = questions.find { it.id == questionId }
            ?: throw IllegalStateException("Question with ID $questionId not found")
        return question.correctOptionId == selectedAnswerId
    }
}
package com.example.movietheatre.feature_movie_quiz.data.repository

import com.example.movietheatre.feature_movie_quiz.data.remote.service.QuizService
import com.example.movietheatre.feature_movie_quiz.domain.repository.SubmitAnswerRepository
import retrofit2.HttpException
import javax.inject.Inject

class SubmitAnswerRepositoryImpl @Inject constructor(
    private val quizService: QuizService
) : SubmitAnswerRepository {

    override suspend fun submitAnswer(
        questionId: Int,
        selectedAnswerId: String
    ): Boolean {
        // 1) Fetch the quiz questions (we treat questionId as the quiz ID in your service)
        val response = quizService.getQuizQuestionsById(questionId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }

        // 2) Extract the questions from the response
        val questions = response.body()
            ?: throw IllegalStateException("No question data returned for ID $questionId")

        // 3) Find the specific question with matching ID (if needed)
        // If questionId is the quiz ID and you need to find a specific question:
        val question = questions.find { it.id == questionId }
            ?: throw IllegalStateException("Question with ID $questionId not found")

        // 4) Compare with the user's selection
        return question.correctOptionId == selectedAnswerId
    }
}
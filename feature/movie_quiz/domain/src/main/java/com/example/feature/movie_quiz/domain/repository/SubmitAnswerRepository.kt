package com.example.feature.movie_quiz.domain.repository

interface SubmitAnswerRepository {
    suspend fun submitAnswer(questionId: Int, selectedAnswerId: String): Boolean
}
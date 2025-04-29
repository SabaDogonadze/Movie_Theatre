package com.example.feature.movie_quiz.data.remote.service

import com.example.feature.movie_quiz.data.remote.model.QuizCategoryDto
import com.example.feature.movie_quiz.data.remote.model.QuizCompletedDto
import com.example.feature.movie_quiz.data.remote.model.QuizQuestionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuizService {

    @GET("quiz/quizzes")
    suspend fun getAllQuizzes(): Response<List<QuizCategoryDto>>

    @GET("quiz/quizzes/{id}/questions")
    suspend fun getQuizQuestionsById(@Path("id") id: Int): Response<List<QuizQuestionDto>>

    @GET("quiz/users/{user_id}/quizzes/completed")
    suspend fun getQuizCompleted(@Path("user_id") userId: String): Response<List<QuizCompletedDto>>

    @POST("quiz/users/{user_id}/quizzes/{id}/complete")
    suspend fun quizUpdate(@Path("user_id") userId: String,@Path("id") id: Int): Response<List<QuizCompletedDto>>

}
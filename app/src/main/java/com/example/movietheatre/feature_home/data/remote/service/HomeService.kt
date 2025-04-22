package com.example.movietheatre.feature_home.data.remote.service

import com.example.movietheatre.feature_home.data.remote.model.HomeMovieListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDateTime

interface HomeService {

    @GET("movies")
    suspend fun getMovieList(
        @Query("search") search: String?,
        @Query("genreId") genreId: Int?,
        @Query("startTime") startTime: LocalDateTime?,
        @Query("endTime") endTime: LocalDateTime?,
    ): Response<List<HomeMovieListResponseDto>>

    @GET("movies/upcoming")
    suspend fun getUpcomingMovies(): Response<List<HomeMovieListResponseDto>>

    @GET("movies/popular")
    suspend fun getPopularMovies(): Response<List<HomeMovieListResponseDto>>
}
package com.example.feature.movie_detail.data.remote.service

import com.example.feature.movie_detail.data.remote.model.MovieDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailService {
    @GET("movies/{id}")
    suspend fun getMovieDetail(@Path("id") id: Int): Response<MovieDetailDto>
}
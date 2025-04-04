package com.example.movietheatre.feauture_movie_detail.data.remote.service

import com.example.movietheatre.feature_home.data.remote.model.GenreListResponseDto
import com.example.movietheatre.feauture_movie_detail.data.remote.model.MovieDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailService {
    @GET("movies/{id}")
    suspend fun getMovieDetail(@Path("id") id: Int): Response<MovieDetailDto>
}
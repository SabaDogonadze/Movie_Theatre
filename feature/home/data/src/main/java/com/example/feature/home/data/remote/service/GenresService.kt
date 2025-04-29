package com.example.feature.home.data.remote.service

import com.example.feature.home.data.remote.model.GenreListResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface GenresService {
    @GET("movies/genres")
    suspend fun getGenreList(): Response<List<GenreListResponseDto>>
}
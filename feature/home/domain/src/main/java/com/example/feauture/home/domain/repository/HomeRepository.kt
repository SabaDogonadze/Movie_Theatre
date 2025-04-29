package com.example.feauture.home.domain.repository

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feauture.home.domain.model.HomeMovieListResponse
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface HomeRepository {
    fun getMovieList(
        endTime: LocalDateTime?,
        genreId: Int?,
        search: String?,
        startTime: LocalDateTime?,
    ): Flow<Resource<List<HomeMovieListResponse>, NetworkError>>


    suspend fun getUpcomingMovies(
    ): Resource<List<HomeMovieListResponse>, NetworkError>

    suspend fun getPopularMovies(
    ): Resource<List<HomeMovieListResponse>, NetworkError>

}
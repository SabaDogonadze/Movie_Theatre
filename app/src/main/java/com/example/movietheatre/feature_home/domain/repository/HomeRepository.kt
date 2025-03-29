package com.example.movietheatre.feature_home.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_home.domain.model.HomeMovieListResponse
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface HomeRepository {
    fun getMovieList(
        endTime: LocalDateTime?,
        genreId: Int?,
        search: String?,
        startTime: LocalDateTime?,
    ): Flow<Resource<List<HomeMovieListResponse>, NetworkError>>
}
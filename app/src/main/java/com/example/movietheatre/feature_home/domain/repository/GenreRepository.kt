package com.example.movietheatre.feature_home.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_home.domain.model.GenreListResponse
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenreList(): Flow<Resource<List<GenreListResponse>, NetworkError>>
}
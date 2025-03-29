package com.example.movietheatre.feature_home.domain.repository

import com.example.movietheatre.core.domain.common.Resource
import com.example.movietheatre.feature_home.domain.model.GenreListResponse
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenreList(): Flow<Resource<List<GenreListResponse>>>
}
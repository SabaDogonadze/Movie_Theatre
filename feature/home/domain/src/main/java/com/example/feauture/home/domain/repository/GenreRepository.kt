package com.example.feauture.home.domain.repository

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feauture.home.domain.model.GenreListResponse
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenreList(): Flow<Resource<List<GenreListResponse>, NetworkError>>
}
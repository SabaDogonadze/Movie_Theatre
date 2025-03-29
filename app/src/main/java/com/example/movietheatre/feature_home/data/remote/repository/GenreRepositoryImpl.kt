package com.example.movietheatre.feature_home.data.remote.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.common.Resource
import com.example.movietheatre.feature_home.data.remote.mapper.toDomain
import com.example.movietheatre.feature_home.data.remote.service.GenresService
import com.example.movietheatre.feature_home.domain.model.GenreListResponse
import com.example.movietheatre.feature_home.domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(private val genresService: GenresService) :
    GenreRepository {
    override fun getGenreList(): Flow<Resource<List<GenreListResponse>>> = flow {
        emit(Resource.Loading(load = true))
        val resource: Resource<List<GenreListResponse>> = ApiHelper.handleHttpRequest(
            apiCall = {
                genresService.getGenreList()
            },
            mapper = { dtoList ->
                dtoList.map { it.toDomain() }
            }
        )
        emit(resource)
    }
}

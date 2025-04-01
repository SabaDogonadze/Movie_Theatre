package com.example.movietheatre.feature_home.data.remote.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_home.data.remote.mapper.toDomain
import com.example.movietheatre.feature_home.data.remote.service.GenresService
import com.example.movietheatre.feature_home.domain.model.GenreListResponse
import com.example.movietheatre.feature_home.domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper, private val genresService: GenresService,
) :
    GenreRepository {
    override fun getGenreList(): Flow<Resource<List<GenreListResponse>, NetworkError>> = flow {

        val resource = apiHelper.handleHttpRequest(
            apiCall = {
                genresService.getGenreList()
            }
        ).mapData { genreDto -> genreDto.map { it.toDomain() } }
        emit(resource)
    }
}

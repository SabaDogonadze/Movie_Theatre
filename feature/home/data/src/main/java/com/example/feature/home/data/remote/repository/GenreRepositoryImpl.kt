package com.example.feature.home.data.remote.repository

import com.example.core.domain.extension.mapData
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.home.data.remote.mapper.toDomain
import com.example.feature.home.data.remote.service.GenresService
import com.example.feauture.home.domain.model.GenreListResponse
import com.example.feauture.home.domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val apiHelper: com.example.core.data.common.ApiHelper,
    private val genresService: GenresService,
) :
    GenreRepository {
    override fun getGenreList(): Flow<Resource<List<GenreListResponse>, NetworkError>> =
        flow {

            val resource = apiHelper.handleHttpRequest(
                apiCall = {
                    genresService.getGenreList()
                }
            ).mapData { genreDto -> genreDto.map { it.toDomain() } }
            emit(resource)
        }
}

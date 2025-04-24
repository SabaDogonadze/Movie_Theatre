package com.example.feature.movie_detail.data.remote.repository

import com.example.core.domain.extension.mapData
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.movie_detail.data.remote.mapper.toDomain
import com.example.feature.movie_detail.data.remote.service.MovieDetailService
import com.example.feature.movie_detail.domain.model.MovieDetail
import com.example.feature.movie_detail.domain.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val movieDetailService: MovieDetailService,
    private val apiHelper: com.example.core.data.common.ApiHelper,
) :
    MovieDetailRepository {
    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail, NetworkError>> {
        return flow {
            val resource = apiHelper.handleHttpRequest(
                apiCall = {
                    movieDetailService.getMovieDetail(movieId)
                }
            ).mapData { genreDto -> genreDto.toDomain() }
            emit(resource)
        }
    }
}
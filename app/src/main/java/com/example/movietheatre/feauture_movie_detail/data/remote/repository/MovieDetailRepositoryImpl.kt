package com.example.movietheatre.feauture_movie_detail.data.remote.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feauture_movie_detail.data.remote.mapper.toDomain
import com.example.movietheatre.feauture_movie_detail.data.remote.service.MovieDetailService
import com.example.movietheatre.feauture_movie_detail.domain.model.MovieDetail
import com.example.movietheatre.feauture_movie_detail.domain.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(private val movieDetailService: MovieDetailService, private val apiHelper: ApiHelper,) :
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
package com.example.movietheatre.feature_home.data.remote.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.common.Resource
import com.example.movietheatre.feature_home.data.remote.service.HomeService
import com.example.movietheatre.feature_home.data.remote.mapper.toDomain
import com.example.movietheatre.feature_home.domain.model.HomeMovieListResponse
import com.example.movietheatre.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeService: HomeService) :
    HomeRepository {
    override fun getMovieList(
        endTime: LocalDateTime?,
        genreId: Int?,
        search: String?,
        startTime: LocalDateTime?,
    ): Flow<Resource<List<HomeMovieListResponse>>> = flow {
        emit(Resource.Loading(load = true))
        val resource: Resource<List<HomeMovieListResponse>> = ApiHelper.handleHttpRequest(
            apiCall = {
                homeService.getMovieList(
                    endTime = endTime,
                    genreId = genreId,
                    search = search,
                    startTime = startTime
                )
            },
            mapper = { dtoList ->
                dtoList.map { it.toDomain() }
            }
        )
        emit(resource)
    }
}

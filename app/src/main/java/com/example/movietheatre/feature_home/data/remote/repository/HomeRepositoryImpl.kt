package com.example.movietheatre.feature_home.data.remote.repository

import com.example.movietheatre.core.data.common.ApiHelper
import com.example.movietheatre.core.domain.extension.mapData
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_home.data.remote.mapper.toDomain
import com.example.movietheatre.feature_home.data.remote.service.HomeService
import com.example.movietheatre.feature_home.domain.model.HomeMovieListResponse
import com.example.movietheatre.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val homeService: HomeService,
) :
    HomeRepository {
    override fun getMovieList(
        endTime: LocalDateTime?,
        genreId: Int?,
        search: String?,
        startTime: LocalDateTime?,
    ): Flow<Resource<List<HomeMovieListResponse>, NetworkError>> = flow {
        val resource = apiHelper.handleHttpRequest(
            apiCall = {
                homeService.getMovieList(
                    endTime = endTime,
                    genreId = genreId,
                    search = search,
                    startTime = startTime
                )
            }
        ).mapData { movieDto -> movieDto.map { it.toDomain() } }
        emit(resource)
    }

    override suspend fun getUpcomingMovies(): Resource<List<HomeMovieListResponse>, NetworkError> {
        return apiHelper.handleHttpRequest { homeService.getUpcomingMovies() }
            .mapData { it.map { it.toDomain() } }
    }
}

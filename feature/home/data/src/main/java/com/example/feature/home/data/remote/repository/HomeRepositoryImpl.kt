package com.example.feature.home.data.remote.repository

import com.example.core.domain.extension.mapData
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.home.data.remote.mapper.toDomain
import com.example.feature.home.data.remote.service.HomeService
import com.example.feauture.home.domain.model.HomeMovieListResponse
import com.example.feauture.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiHelper: com.example.core.data.common.ApiHelper,
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

    override suspend fun getPopularMovies(): Resource<List<HomeMovieListResponse>, NetworkError> {
        return apiHelper.handleHttpRequest { homeService.getPopularMovies() }
            .mapData { it.map { it.toDomain() } }
    }
}

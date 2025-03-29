package com.example.movietheatre.feature_home.domain.usecase

import com.example.movietheatre.core.domain.common.Resource
import com.example.movietheatre.feature_home.domain.model.HomeMovieListResponse
import com.example.movietheatre.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

interface GetMovieListUseCase {
    operator fun invoke(
        endTime: LocalDateTime? = null,
        genreId: Int? = null,
        search: String? = null,
        startTime: LocalDateTime? = null
    ): Flow<Resource<List<HomeMovieListResponse>>>
}

class GetMovieListUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : GetMovieListUseCase {
    override operator fun invoke(
        endTime: LocalDateTime?,
        genreId: Int?,
        search: String?,
        startTime: LocalDateTime?
    ): Flow<Resource<List<HomeMovieListResponse>>> {
        return homeRepository.getMovieList(endTime, genreId, search, startTime)
    }
}
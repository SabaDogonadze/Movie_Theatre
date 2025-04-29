package com.example.feauture.home.domain.usecase

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feauture.home.domain.model.HomeMovieListResponse
import com.example.feauture.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

interface GetMovieListUseCase {
    operator fun invoke(
        endTime: LocalDateTime? = null,
        genreId: Int? = null,
        search: String? = null,
        startTime: LocalDateTime? = null,
    ): Flow<Resource<List<HomeMovieListResponse>, NetworkError>>
}

class GetMovieListUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository,
) : GetMovieListUseCase {
    override operator fun invoke(
        endTime: LocalDateTime?,
        genreId: Int?,
        search: String?,
        startTime: LocalDateTime?,
    ): Flow<Resource<List<HomeMovieListResponse>, NetworkError>> {
        return homeRepository.getMovieList(endTime, genreId, search, startTime)
    }
}
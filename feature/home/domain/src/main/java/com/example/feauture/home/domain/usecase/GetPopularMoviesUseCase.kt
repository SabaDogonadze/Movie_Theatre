package com.example.feauture.home.domain.usecase

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feauture.home.domain.model.HomeMovieListResponse
import com.example.feauture.home.domain.repository.HomeRepository
import javax.inject.Inject


class GetPopularMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {
    suspend operator fun invoke(
    ): Resource<List<HomeMovieListResponse>, NetworkError> {
        return homeRepository.getPopularMovies()
    }
}
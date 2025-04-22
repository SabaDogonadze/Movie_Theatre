package com.example.movietheatre.feature_home.domain.usecase

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_home.domain.model.HomeMovieListResponse
import com.example.movietheatre.feature_home.domain.repository.HomeRepository
import javax.inject.Inject


class GetPopularMoviesUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) {
    suspend operator fun invoke(
    ): Resource<List<HomeMovieListResponse>, NetworkError> {
        return homeRepository.getPopularMovies()
    }
}
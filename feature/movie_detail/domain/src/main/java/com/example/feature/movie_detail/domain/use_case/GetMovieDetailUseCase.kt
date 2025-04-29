package com.example.feature.movie_detail.domain.use_case

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feature.movie_detail.domain.model.MovieDetail
import com.example.feature.movie_detail.domain.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMovieDetailUseCase {
    operator fun invoke(movieId: Int): Flow<Resource<MovieDetail, NetworkError>>
}

class GetMovieDetailUseCaseImpl @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository,
) : GetMovieDetailUseCase {
    override fun invoke(movieId: Int): Flow<Resource<MovieDetail, NetworkError>> {
        return movieDetailRepository.getMovieDetail(movieId)
    }

}
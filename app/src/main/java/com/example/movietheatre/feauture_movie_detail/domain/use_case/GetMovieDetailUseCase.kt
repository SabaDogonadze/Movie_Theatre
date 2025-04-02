package com.example.movietheatre.feauture_movie_detail.domain.use_case

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feauture_movie_detail.domain.model.MovieDetail
import com.example.movietheatre.feauture_movie_detail.domain.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMovieDetailUseCase {
    operator fun invoke(movieId:Int): Flow<Resource<MovieDetail, NetworkError>>
}

class GetMovieDetailUseCaseImpl @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) : GetMovieDetailUseCase {
    override fun invoke(movieId: Int): Flow<Resource<MovieDetail, NetworkError>> {
       return movieDetailRepository.getMovieDetail(movieId)
    }

}
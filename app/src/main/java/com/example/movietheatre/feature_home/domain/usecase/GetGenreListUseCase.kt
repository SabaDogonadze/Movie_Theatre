package com.example.movietheatre.feature_home.domain.usecase

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_home.domain.model.GenreListResponse
import com.example.movietheatre.feature_home.domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetGenreListUseCase {
    operator fun invoke(): Flow<Resource<List<GenreListResponse>, NetworkError>>
}

class GetGenreListUseCaseImpl @Inject constructor(
    private val genreRepository: GenreRepository,
) : GetGenreListUseCase {
    override operator fun invoke(): Flow<Resource<List<GenreListResponse>, NetworkError>> {
        return genreRepository.getGenreList()
    }
}
package com.example.feauture.home.domain.usecase

import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import com.example.feauture.home.domain.model.GenreListResponse
import com.example.feauture.home.domain.repository.GenreRepository
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
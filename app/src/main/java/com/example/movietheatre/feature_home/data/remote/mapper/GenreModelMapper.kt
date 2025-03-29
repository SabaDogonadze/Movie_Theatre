package com.example.movietheatre.feature_home.data.remote.mapper

import com.example.movietheatre.feature_home.data.remote.model.GenreListResponseDto
import com.example.movietheatre.feature_home.domain.model.GenreListResponse

fun GenreListResponseDto.toDomain(): GenreListResponse {
    return GenreListResponse(id = id, name = name)
}
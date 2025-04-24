package com.example.feature.home.data.remote.mapper

import com.example.feature.home.data.remote.model.GenreListResponseDto
import com.example.feauture.home.domain.model.GenreListResponse

fun GenreListResponseDto.toDomain(): GenreListResponse {
    return GenreListResponse(id = id, name = name)
}
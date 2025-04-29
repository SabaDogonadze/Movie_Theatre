package com.example.feature.home.data.remote.mapper

import com.example.feature.home.data.remote.model.HomeMovieListResponseDto
import com.example.feauture.home.domain.model.HomeMovieListResponse

fun HomeMovieListResponseDto.toDomain(): HomeMovieListResponse {
    return HomeMovieListResponse(
        id = id,
        title = title,
        description = description,
        movieImgUrl = movieImgUrl,
        duration = duration
    )
}
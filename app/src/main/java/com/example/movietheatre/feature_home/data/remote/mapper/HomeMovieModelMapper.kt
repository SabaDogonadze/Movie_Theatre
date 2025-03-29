package com.example.movietheatre.feature_home.data.remote.mapper

import com.example.movietheatre.feature_home.data.remote.model.HomeMovieListResponseDto
import com.example.movietheatre.feature_home.domain.model.HomeMovieListResponse

fun HomeMovieListResponseDto.toDomain(): HomeMovieListResponse {
    return HomeMovieListResponse(
        id = id,
        title = title,
        description = description,
        movieImgUrl = movieImgUrl,
        duration = duration
    )
}
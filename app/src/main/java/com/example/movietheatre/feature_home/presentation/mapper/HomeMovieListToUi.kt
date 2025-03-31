package com.example.movietheatre.feature_home.presentation.mapper

import com.example.movietheatre.feature_home.domain.model.HomeMovieListResponse
import com.example.movietheatre.feature_home.presentation.model.HomeMovieListUi

fun HomeMovieListResponse.toUi(): HomeMovieListUi {
    return HomeMovieListUi(
        id = id,
        title = title,
        description = description,
        movieImgUrl = movieImgUrl,
        duration = duration
    )
}
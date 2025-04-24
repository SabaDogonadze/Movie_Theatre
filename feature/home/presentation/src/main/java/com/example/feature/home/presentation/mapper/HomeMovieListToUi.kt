package com.example.feature.home.presentation.mapper

import com.example.feature.home.presentation.model.HomeMovieListUi
import com.example.feauture.home.domain.model.HomeMovieListResponse

fun HomeMovieListResponse.toPresentation(): HomeMovieListUi {
    return HomeMovieListUi(
        id = id,
        title = title,
        description = description,
        movieImgUrl = movieImgUrl,
        duration = duration
    )
}
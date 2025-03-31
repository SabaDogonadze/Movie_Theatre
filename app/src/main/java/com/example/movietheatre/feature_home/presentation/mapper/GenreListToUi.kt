package com.example.movietheatre.feature_home.presentation.mapper

import com.example.movietheatre.feature_home.domain.model.GenreListResponse
import com.example.movietheatre.feature_home.presentation.model.GenresListUi

fun GenreListResponse.toUi(): GenresListUi {
    return GenresListUi(id = id, name = name)
}
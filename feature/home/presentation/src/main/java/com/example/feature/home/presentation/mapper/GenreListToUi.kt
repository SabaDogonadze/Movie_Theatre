package com.example.feature.home.presentation.mapper

import com.example.feature.home.presentation.model.GenresListUi
import com.example.feauture.home.domain.model.GenreListResponse

fun GenreListResponse.toPresentation(): GenresListUi {
    return GenresListUi(id = id, name = name)
}
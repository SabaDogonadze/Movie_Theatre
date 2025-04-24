package com.example.feature.home.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponseDto(
    val id : Int,
    val name:String
)

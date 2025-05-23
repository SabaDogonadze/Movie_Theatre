package com.example.feature.home.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class HomeMovieListResponseDto(
    val id:Int,
    val title:String,
    val description:String,
    val movieImgUrl:String,
    val duration:Int
)

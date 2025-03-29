package com.example.movietheatre.feature_home.domain.model

data class HomeMovieListResponse (
    val id:Int,
    val title:String,
    val description:String,
    val movieImgUrl:String,
    val duration:Int
)

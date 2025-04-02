package com.example.movietheatre.feauture_movie_detail.domain.repository

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feauture_movie_detail.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun getMovieDetail(movieId:Int): Flow<Resource<MovieDetail, NetworkError>>
}
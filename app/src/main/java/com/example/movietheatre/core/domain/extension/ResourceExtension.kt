package com.example.movietheatre.core.domain.extension

import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.RootError


fun <T, R, E : RootError> Resource<T, E>.mapData(transform: (T) -> R): Resource<R, E> =
    when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(error)
    }
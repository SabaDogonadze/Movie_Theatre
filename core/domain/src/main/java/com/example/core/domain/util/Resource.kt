package com.example.core.domain.util

import com.example.core.domain.util.error.RootError

/**
 * the D stands for Data,and E - for Error
 * */

sealed interface Resource<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Resource<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Resource<D, E>
}
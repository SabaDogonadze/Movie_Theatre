package com.example.core.domain.util.error

sealed class NetworkError : RootError {
    data object ConnectionError : NetworkError()
    data class ServerError(val exception: Int) : NetworkError()
    data object EmptyResponse : NetworkError()
    data object UnknownError : NetworkError()

}
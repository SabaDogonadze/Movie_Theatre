package com.example.movietheatre.core.data.common

import com.example.movietheatre.core.domain.common.Resource
import retrofit2.Response


object ApiHelper {
    suspend fun <T, R> handleHttpRequest(
        apiCall: suspend () -> Response<T>,
        mapper: (T) -> R,
    ): Resource<R> {
        val response = apiCall.invoke()
        return try {
            if (response.isSuccessful) {
                val body = response.body()?.let {
                    Resource.Success(dataSuccess = mapper(it))
                }
                    ?: Resource.Error(errorMessage = "Response is null") // without it it does not work because it returns null
                return body
            } else {
                Resource.Error(errorMessage = response.errorBody()?.string() ?: "Unknown error")
            }
        } catch (e: Exception) {
            Resource.Error(errorMessage = e.message ?: "An error")
        }
    }

}
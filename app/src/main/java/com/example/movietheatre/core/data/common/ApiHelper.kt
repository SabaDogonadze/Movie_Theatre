package com.example.movietheatre.core.data.common

import android.util.Log
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import okio.IOException
import retrofit2.Response
import javax.inject.Inject


class ApiHelper @Inject constructor() {
    suspend fun <T> handleHttpRequest(
        apiCall: suspend () -> Response<T>,
    ): Resource<T, NetworkError> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    Resource.Success(data)
                } ?: Resource.Error(NetworkError.EmptyResponse)
            } else {
                Resource.Error(NetworkError.UnknownError)
            }
        } catch (e: IOException) {
            Resource.Error(NetworkError.ConnectionError)
        } catch (e: Exception) {
            Log.d("eror",e.toString())
            Resource.Error(NetworkError.ServerError(e))
        }
    }

}
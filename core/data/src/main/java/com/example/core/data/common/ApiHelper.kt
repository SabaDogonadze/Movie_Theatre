package com.example.core.data.common

import android.util.Log
import com.example.core.domain.util.Resource
import com.example.core.domain.util.error.NetworkError
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class ApiHelper @Inject constructor(/* dependencies */) {

    suspend fun <T> handleHttpRequest(apiCall: suspend () -> Response<T>): Resource<T, NetworkError> {
        return try {
            val response = apiCall()
            Log.d("API", "Response code: ${response.code()}")
            Log.d("API", "Response body: ${response.body()}")

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.Success(body)
                } else {
                    Log.d("API", "Empty response body")
                    Resource.Error(NetworkError.EmptyResponse)
                }
            } else {
                Log.d("API", "Server error with code: ${response.code()}")
                Log.d("API", "Error body: ${response.errorBody()?.string()}")
                Resource.Error(NetworkError.ServerError(response.code()))
            }
        } catch (e: IOException) {
            Log.e("API", "Connection error: ${e.javaClass.simpleName} - ${e.message}", e)
            Resource.Error(NetworkError.ConnectionError)
        } catch (e: Exception) {
            Log.e("API", "Unknown error: ${e.javaClass.simpleName} - ${e.message}", e)
            e.printStackTrace()
            Resource.Error(NetworkError.UnknownError)
        }
    }
}

/*
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

}*/

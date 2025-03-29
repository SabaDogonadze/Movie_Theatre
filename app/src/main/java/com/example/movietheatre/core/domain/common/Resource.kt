package com.example.movietheatre.core.domain.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class Resource<T>(
    val data: T? = null,
    val error:String? = null, // error can be a string, statusCode, so it should be a generic type ( but i have some error in a viewmodel so , it is a string for now)
    val loading:Boolean = false // this could be an object
) {
    data class Success<T>(val dataSuccess: T) : Resource<T>(data = dataSuccess)
    class Error<T>(val errorMessage: String) : Resource<T>(error = errorMessage)  // data class is not necessary
    class Loading<T>( load: Boolean) : Resource<T>(loading = load) // data class is not necessary

}


fun <DTO,DOMAIN> Flow<Resource<DTO>>.mapResource(mapper:(DTO)->DOMAIN):Flow<Resource<DOMAIN>>{
    return map { resource ->
        when(resource){
            is Resource.Success -> {
                Resource.Success(dataSuccess = mapper(resource.dataSuccess))
            }
            is Resource.Error -> {
                Resource.Error(errorMessage = resource.errorMessage)
            }
            is Resource.Loading -> {
                Resource.Loading(load = resource.loading)
            }
        }
    }
}
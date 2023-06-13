package com.example.openai_app.model.remote

sealed class NetworkResult<T: Any>{
    data class Success<T: Any>(val data: T?): NetworkResult<T>()
    data class Error<T: Any>(val code: Int, val mess: String?): NetworkResult<T>()
    data class Exception<T: Any>(val e: Throwable): NetworkResult<T>()
    class Loading<T: Any>: NetworkResult<T>()
}

package com.fitflow.core_network.result

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>
    data class NetworkError(val throwable: Throwable) : ApiResult<Nothing>
}
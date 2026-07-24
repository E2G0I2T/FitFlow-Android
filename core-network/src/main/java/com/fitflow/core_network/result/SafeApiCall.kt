package com.fitflow.core_network.result

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: HttpException) {
        ApiResult.Error(code = e.code(), message = e.message() ?: "서버 오류가 발생했습니다")
    } catch (e: IOException) {
        ApiResult.NetworkError(e)
    }
}
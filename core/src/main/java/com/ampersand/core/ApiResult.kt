package com.ampersand.core

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(
        val message: UiText? = null,
        val throwable: Throwable? = null
    ) : ApiResult<T>()
}

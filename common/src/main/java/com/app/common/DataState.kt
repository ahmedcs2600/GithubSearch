package com.app.common

sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val message: String) : DataState<T>()

    companion object {
        fun <T> success(data: T): Success<T> = Success(data)
        fun <T> error(message: String) = Error<T>(message)
    }
}

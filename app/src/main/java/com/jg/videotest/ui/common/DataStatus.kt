package com.jg.videotest.ui.common

sealed class DataStatus<out T> {
    data class Success<T>(val data: T) : DataStatus<T>()
    data class Error<T>(val errorMessage: String?) : DataStatus<T>()
    data class Empty<T>(val emptyMessage: String?) : DataStatus<T>()
    class Loading<T> : DataStatus<T>()
}
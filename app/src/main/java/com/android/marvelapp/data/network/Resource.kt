package com.android.marvelapp.data.network

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
}

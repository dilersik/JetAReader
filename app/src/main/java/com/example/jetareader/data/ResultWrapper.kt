package com.example.jetareader.data

sealed class ResultWrapper<T> {
    class Error<T>(val exception: Exception): ResultWrapper<T>()
    class Success<T>(val data: T): ResultWrapper<T>()
    class Loading<T>(val data: T? = null): ResultWrapper<T>()
}
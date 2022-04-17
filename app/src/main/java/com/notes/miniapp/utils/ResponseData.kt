package com.notes.miniapp.utils

sealed class ResponseData<out T : Any> {
    object Loading : ResponseData<Nothing>()
    data class Success<out T : Any>(val data: T) : ResponseData<T>()
    data class Error(val msg: String) : ResponseData<Nothing>()

    fun message(): String {
        return when (this) {
            is Loading -> "Loading"
            is Success -> "Success"
            is Error -> msg
        }
    }
}

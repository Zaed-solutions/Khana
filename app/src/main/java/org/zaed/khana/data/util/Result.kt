package org.zaed.khana.data.util

import kotlinx.serialization.Serializable

typealias RootError = Error

@Serializable
sealed interface Result<out D, out E : RootError> {
    @Serializable
    data object Loading : Result<Nothing, Nothing>

    @Serializable
    data class Success<out D>(val data: D) : Result<D, Nothing>

    @Serializable
    data class Error<out E : RootError>(val message: E) : Result<Nothing, E>

    fun isSuccessful(): Boolean = this is Success

    companion object {
        fun <D> success(data: D): Result<D, Nothing> = Success(data)
        fun <E : RootError> failure(error: E): Result<Nothing, E> = Error(error)
        fun idle(): Result<Nothing, Nothing> = Loading
    }

    fun onSuccessWithData(block: (D) -> Unit): Result<D,E> {
        if (this is Success) {
            block(data)
        }
        return this
    }

    fun onSuccess(block: () -> Unit): Result<D,E> {
        if (this is Success) {
            block()
        }
        return this
    }

    fun onLoading(block: () -> Unit): Result<D,E> {
        if (this is Loading) {
            block()
        }
        return this
    }
    fun onFailure(block: (E) -> Unit): Result<D,E> {
        if (this is Error) {
            block(message)
        }
        return this
    }
}
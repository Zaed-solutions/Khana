package org.zaed.khana.data.source.remote.util

import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse<T> (
    val code: Int = 0,
    val message: String="",
    val data: T? =null,
)
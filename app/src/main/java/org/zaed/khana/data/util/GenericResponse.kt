package org.zaed.khana.data.util

import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse<T> (
    val code: Int = 0,
    val message: String="",
    val data: T? =null,
)
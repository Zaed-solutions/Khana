package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Advertisement(
    val title: String,
    val description: String,
    val backgroundImageUrl: String,
)

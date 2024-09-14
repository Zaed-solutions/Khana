package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val categoryImage: String = "",
    val categoryTitle: String = "",
)

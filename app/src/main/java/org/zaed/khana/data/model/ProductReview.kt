package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductReview(
    val id: String = "",
    val userId: String = "",
    val productId: String = "",
    val rating: Int = 0,
    val review: String = "",
)



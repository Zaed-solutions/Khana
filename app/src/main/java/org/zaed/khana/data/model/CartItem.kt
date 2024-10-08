package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: String = "",
    val userId: String = "",
    val productId: String = "",
    val productThumbnail: String = "",
    val productName: String = "",
    val productColor: Color = Color(),
    val productSize: String = "",
    val productBasePrice: Float = 0f,
    val quantity: Int = 0,
)

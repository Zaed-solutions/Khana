package org.zaed.khana.data.source.remote.model.request

import kotlinx.serialization.Serializable

sealed interface ProductRequest {
    @Serializable
    data class FetchProductsByLabelRequest(val label: String): ProductRequest
    @Serializable
    data class FetchWishlistedProductsIds(val userId: String): ProductRequest
    @Serializable
    data class AddWishlistedProduct(val productId: String, val userId: String): ProductRequest
    @Serializable
    data class RemoveWishlistedProduct(val productId: String, val userId: String): ProductRequest
}
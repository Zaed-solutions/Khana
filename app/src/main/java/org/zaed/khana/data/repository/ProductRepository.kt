package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Product

interface ProductRepository {
    fun fetchLabels(): Flow<List<String>>
    fun fetchProductsByLabel(label: String): Flow<List<Product>>
    fun fetchWishlistedProductsIds(userId: String): Flow<List<String>>
    suspend fun addWishlistedProduct(productId: String, userId: String)
    suspend fun removeWishlistedProduct(productId: String, userId: String)
}
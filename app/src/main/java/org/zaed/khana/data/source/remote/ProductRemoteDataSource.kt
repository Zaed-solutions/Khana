package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.source.remote.model.request.ProductRequest

interface ProductRemoteDataSource {
    fun fetchLabels(): Flow<List<String>>
    suspend fun fetchFlashSaleEndTime(): Long
    fun fetchProductsByLabel(request: ProductRequest.FetchProductsByLabelRequest): Flow<List<Product>>
    fun fetchWishlistedProductsIds(request: ProductRequest.FetchWishlistedProductsIds): Flow<List<String>>
    suspend fun addWishlistedProduct(request: ProductRequest.AddWishlistedProduct)
    suspend fun removeWishlistedProduct(request: ProductRequest.RemoveWishlistedProduct)
}
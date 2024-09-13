package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.source.remote.ProductRemoteDataSource
import org.zaed.khana.data.source.remote.model.request.ProductRequest

class ProductRepositoryImpl (
    private val productRemoteSource: ProductRemoteDataSource
): ProductRepository {
    override fun fetchLabels(): Flow<List<String>> {
        return productRemoteSource.fetchLabels()
    }

    override fun fetchProductsByLabel(label: String): Flow<List<Product>> {
        val request = ProductRequest.FetchProductsByLabelRequest(label)
        return productRemoteSource.fetchProductsByLabel(request)
    }

    override fun fetchWishlistedProductsIds(userId: String): Flow<List<String>> {
        val request = ProductRequest.FetchWishlistedProductsIds(userId)
        return productRemoteSource.fetchWishlistedProductsIds(request)
    }

    override suspend fun addWishlistedProduct(productId: String, userId: String) {
        val request = ProductRequest.AddWishlistedProduct(productId = productId, userId = userId)
        return productRemoteSource.addWishlistedProduct(request)
    }

    override suspend fun removeWishlistedProduct(productId: String, userId: String) {
        val request = ProductRequest.RemoveWishlistedProduct(productId = productId, userId = userId)
        return productRemoteSource.removeWishlistedProduct(request)
    }

    override suspend fun fetchFlashSaleEndTime(): Long {
        return productRemoteSource.fetchFlashSaleEndTime()
    }
}
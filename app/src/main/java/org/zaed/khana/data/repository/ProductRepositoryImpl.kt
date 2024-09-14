package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.source.remote.ProductRemoteDataSource
import org.zaed.khana.data.source.remote.model.request.ProductRequest
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result

class ProductRepositoryImpl (
    private val productRemoteSource: ProductRemoteDataSource
): ProductRepository {
    override fun fetchLabels(): Flow<Result<List<String>, ProductResult>> {
        return productRemoteSource.fetchLabels()
    }

    override fun fetchProductsByLabel(label: String):Flow<Result<List<Product>, ProductResult>> {
        val request = ProductRequest.FetchProductsByLabelRequest(label)
        return productRemoteSource.fetchProductsByLabel(request)
    }

    override fun fetchWishlistedProductsIds(userId: String): Flow<Result<List<String>, ProductResult>> {
        val request = ProductRequest.FetchWishlistedProductsIds(userId)
        return productRemoteSource.fetchWishlistedProductsIds(request)
    }

    override suspend fun addWishlistedProduct(productId: String, userId: String): Result<Unit, ProductResult> {
        val request = ProductRequest.AddWishlistedProduct(productId = productId, userId = userId)
        return productRemoteSource.addWishlistedProduct(request)
    }

    override suspend fun removeWishlistedProduct(productId: String, userId: String): Result<Unit, ProductResult> {
        val request = ProductRequest.RemoveWishlistedProduct(productId = productId, userId = userId)
        return productRemoteSource.removeWishlistedProduct(request)
    }

    override suspend fun fetchFlashSaleEndTime(): Result<Long, ProductResult> {
        return productRemoteSource.fetchFlashSaleEndTime()
    }
}
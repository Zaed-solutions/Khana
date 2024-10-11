package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.model.ProductFilter
import org.zaed.khana.data.source.remote.ProductRemoteDataSource
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result

class ProductRepositoryImpl(
    private val productRemoteSource: ProductRemoteDataSource
): ProductRepository {
    override suspend fun fetchProductById(id: String): Result<Product, ProductResult> {
        return productRemoteSource.fetchProductById(id)
    }

    override suspend fun checkIfIsProductWishlisted(
        userId: String,
        productId: String
    ): Result<Boolean, ProductResult> {
        return productRemoteSource.checkIfIsProductWishlisted(userId = userId, productId = productId)
    }

    override fun fetchLabels(): Flow<Result<List<String>, ProductResult>> {
        return productRemoteSource.fetchLabels()
    }

    override fun fetchProductsByFilter(filter: ProductFilter):Flow<Result<List<Product>, ProductResult>> {
        return productRemoteSource.fetchProductsByFilter(filter = filter)
    }

    override fun fetchWishlistedProductsIds(userId: String): Flow<Result<List<String>, ProductResult>> {
        return productRemoteSource.fetchWishlistedProductsIds(userId = userId)
    }

    override fun fetchWishlistedProducts(userId: String): Flow<Result<List<Product>, ProductResult>> {
        return productRemoteSource.fetchWishlistedProducts(userId = userId)
    }

    override suspend fun addWishlistedProduct(productId: String, userId: String): Result<Unit, ProductResult> {
        return productRemoteSource.addWishlistedProduct(productId = productId, userId = userId)
    }
    override suspend fun removeWishlistedProduct(productId: String, userId: String): Result<Unit, ProductResult> {
        return productRemoteSource.removeWishlistedProduct(productId = productId, userId = userId)
    }

    override suspend fun fetchFlashSaleEndTime(): Result<Long, ProductResult> {
        return productRemoteSource.fetchFlashSaleEndTime()
    }
}
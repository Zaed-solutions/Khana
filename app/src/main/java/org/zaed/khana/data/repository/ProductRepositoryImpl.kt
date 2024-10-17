package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.model.ProductFilter
import org.zaed.khana.data.model.ProductReview
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

    override fun fetchSortedByOptions(): Flow<Result<List<String>, ProductResult>> {
        return productRemoteSource.fetchSortedByOptions()
    }

    override fun fetchProductsByFilter(filter: ProductFilter):Flow<Result<List<Product>, ProductResult>> {
        return productRemoteSource.fetchProductsByFilter(filter = filter)
    }

    override fun fetchProductsByCategory(category: String): Flow<Result<List<Product>, ProductResult>> {
        return productRemoteSource.fetchProductsByCategory(category = category)
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

    override suspend fun addProductReview(review: ProductReview): Result<Unit, ProductResult> {
        return productRemoteSource.addProductReview(review = review)
    }
}
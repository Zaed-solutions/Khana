package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.model.ProductFilter
import org.zaed.khana.data.model.ProductReview
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result

interface ProductRepository {
    suspend fun fetchProductById(id: String): Result<Product, ProductResult>
    suspend fun checkIfIsProductWishlisted(userId: String, productId: String):Result<Boolean, ProductResult>
    fun fetchSortedByOptions():  Flow<Result<List<String>, ProductResult>>
    fun fetchProductsByFilter(filter: ProductFilter): Flow<Result<List<Product>, ProductResult>>
    fun fetchProductsByCategory(category: String): Flow<Result<List<Product>, ProductResult>>
    fun fetchWishlistedProductsIds(userId: String): Flow<Result<List<String>, ProductResult>>
    fun fetchWishlistedProducts(userId: String): Flow<Result<List<Product>, ProductResult>>
    suspend fun addWishlistedProduct(productId: String, userId: String): Result<Unit, ProductResult>
    suspend fun removeWishlistedProduct(
        productId: String,
        userId: String
    ): Result<Unit, ProductResult>

    suspend fun fetchFlashSaleEndTime(): Result<Long, ProductResult>
    suspend fun addProductReview(review: ProductReview): Result<Unit, ProductResult>
}
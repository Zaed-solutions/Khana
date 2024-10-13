package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.model.ProductFilter
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result

interface ProductRemoteDataSource {
    suspend fun fetchProductById(productId: String): Result<Product, ProductResult>
    suspend fun checkIfIsProductWishlisted(userId: String, productId: String): Result<Boolean, ProductResult>
    fun fetchSortedByOptions(): Flow<Result<List<String>, ProductResult>>
    suspend fun fetchFlashSaleEndTime(): Result<Long, ProductResult>
    fun fetchProductsByFilter(filter: ProductFilter): Flow<Result<List<Product>, ProductResult>>
    fun fetchProductsByCategory(category: String): Flow<Result<List<Product>, ProductResult>>
    fun fetchWishlistedProductsIds(userId: String): Flow<Result<List<String>, ProductResult>>
    suspend fun addWishlistedProduct(userId: String, productId: String): Result<Unit, ProductResult>
    suspend fun removeWishlistedProduct(userId: String, productId: String): Result<Unit, ProductResult>
    fun fetchWishlistedProducts(userId: String): Flow<Result<List<Product>, ProductResult>>
    fun searchProductsByTitle(query: String): Flow<Result<List<Product>, ProductResult>>
}
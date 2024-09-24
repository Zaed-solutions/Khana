package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.source.remote.model.request.ProductRequest
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result

interface ProductRemoteDataSource {
    fun fetchLabels(): Flow<Result<List<String>, ProductResult>>
    suspend fun fetchFlashSaleEndTime(): Result<Long, ProductResult>
    fun fetchProductsByLabel(request: ProductRequest.FetchProductsByLabelRequest): Flow<Result<List<Product>, ProductResult>>
    fun fetchWishlistedProductsIds(request: ProductRequest.FetchWishlistedProductsIds): Flow<Result<List<String>, ProductResult>>
    fun fetchWishlistedProducts(request: ProductRequest.FetchWishlistedProducts): Flow<Result<List<Product>, ProductResult>>
    suspend fun addWishlistedProduct(request: ProductRequest.AddWishlistedProduct): Result<Unit, ProductResult>
    suspend fun removeWishlistedProduct(request: ProductRequest.RemoveWishlistedProduct): Result<Unit, ProductResult>
}
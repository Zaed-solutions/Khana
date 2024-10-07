package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.model.Product
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

) : ProductRepository {
    override fun fetchLabels(): Flow<Result<List<String>, ProductResult>> {
        return productRemoteSource.fetchLabels()
    }

    override fun fetchProductsByLabel(label: String):Flow<Result<List<Product>, ProductResult>> {
        return productRemoteSource.fetchProductsByLabel(label = label)
    override fun fetchProductsByLabel(label: String): Flow<Result<List<Product>, ProductResult>> {
        val request = ProductRequest.FetchProductsByLabelRequest(label)
        return productRemoteSource.fetchProductsByLabel(request)
    }

    override fun fetchWishlistedProductsIds(userId: String): Flow<Result<List<String>, ProductResult>> {
        return productRemoteSource.fetchWishlistedProductsIds(userId = userId)
    }

    override suspend fun addWishlistedProduct(productId: String, userId: String): Result<Unit, ProductResult> {
        return productRemoteSource.addWishlistedProduct(productId = productId, userId = userId)
    override fun fetchWishlistedProducts(userId: String): Flow<Result<List<Product>, ProductResult>> {
        val request = ProductRequest.FetchWishlistedProducts(userId)
        return productRemoteSource.fetchWishlistedProducts(request)
    }

    override suspend fun addWishlistedProduct(
        productId: String,
        userId: String
    ): Result<Unit, ProductResult> {
        val request = ProductRequest.AddWishlistedProduct(productId = productId, userId = userId)
        return productRemoteSource.addWishlistedProduct(request)
    }

    override suspend fun removeWishlistedProduct(productId: String, userId: String): Result<Unit, ProductResult> {
        return productRemoteSource.removeWishlistedProduct(productId = productId, userId = userId)
    override suspend fun removeWishlistedProduct(
        productId: String,
        userId: String
    ): Result<Unit, ProductResult> {
        val request = ProductRequest.RemoveWishlistedProduct(productId = productId, userId = userId)
        return productRemoteSource.removeWishlistedProduct(request)
    }

    override suspend fun fetchFlashSaleEndTime(): Result<Long, ProductResult> {
        return productRemoteSource.fetchFlashSaleEndTime()
    }

    override suspend fun addItemToCart(
        userId: String,
        productId: String,
        productColor: Color,
        productSize: String
    ): Result<Unit, ProductResult> {
        return productRemoteSource.addItemToCart(productId = productId, userId = userId, productColor = productColor, productSize = productSize)
    }
}
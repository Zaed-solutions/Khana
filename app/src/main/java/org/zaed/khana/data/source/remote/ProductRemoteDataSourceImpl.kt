package org.zaed.khana.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.model.ProductFilter
import org.zaed.khana.data.model.ProductReview
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.GenericResponse
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result

class ProductRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : ProductRemoteDataSource {
    override suspend fun fetchProductById(productId: String): Result<Product, ProductResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchProductById.route)
                parameter("productId", productId)
            }
            if(response.status == HttpStatusCode.OK){
                val responseData = response.body<GenericResponse<Product>>().data
                if(responseData != null){
                    Result.success(responseData)
                } else {
                    Result.failure(ProductResult.FETCH_PRODUCTS_FAILED)
                }
            } else {
                Result.failure(ProductResult.FETCH_PRODUCTS_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }

    override suspend fun checkIfIsProductWishlisted(userId: String, productId: String): Result<Boolean, ProductResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Product.CheckIfProductIsWishlisted.route)
                parameter("userId", userId)
                parameter("productId", productId)
            }
            if(response.status == HttpStatusCode.OK){
                val responseData = response.body<GenericResponse<Boolean>>().data
                if(responseData != null){
                    Result.success(responseData)
                } else {
                    Result.failure(ProductResult.CHECK_IF_PRODUCT_IS_WISHLISTED_FAILED)
                }
            } else {
                Result.failure(ProductResult.CHECK_IF_PRODUCT_IS_WISHLISTED_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }

    override fun fetchSortedByOptions(): Flow<Result<List<String>, ProductResult>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchSortedByOptions.route)
            }
            if(response.status == HttpStatusCode.OK) {
                val responseData = response.body<GenericResponse<List<String>>>().data
                if(responseData != null){
                    emit(Result.success(responseData))
                } else {
                    emit(Result.failure(ProductResult.FETCH_SORTED_BY_OPTIONS_FAILED))
                }
            } else {
                emit(Result.failure(ProductResult.FETCH_SORTED_BY_OPTIONS_FAILED))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(ProductResult.SERVER_ERROR))
        }
    }

    //fetch flash sale end time in epoch seconds
    override suspend fun fetchFlashSaleEndTime(): Result<Long, ProductResult> {
        return try {
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchFlashSaleEndTime.route)
            }
            if(response.status == HttpStatusCode.OK){
                val responseData = response.body<GenericResponse<Long>>().data
                if(responseData != null){
                    Result.success(responseData)
                } else {
                    Result.failure(ProductResult.FETCH_FLASH_SALE_END_TIME_FAILED)
                }
            } else {
                Result.failure(ProductResult.FETCH_FLASH_SALE_END_TIME_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }

    override fun fetchProductsByFilter(filter: ProductFilter): Flow<Result<List<Product>, ProductResult>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.post {
                endPoint(EndPoint.Product.FetchProductsByFilter.route)
                setBody(
                    filter
                    )
            }
            if(response.status == HttpStatusCode.OK) {
                val responseData = response.body<GenericResponse<List<Product>>>().data
                if(responseData != null){
                    emit(Result.success(responseData))
                } else {
                    emit(Result.failure(ProductResult.FETCH_PRODUCTS_FAILED))
                }
            } else {
                emit(Result.failure(ProductResult.FETCH_PRODUCTS_FAILED))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(ProductResult.SERVER_ERROR))
        }
    }
    override fun fetchProductsByCategory(category: String): Flow<Result<List<Product>, ProductResult>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchProductsByCategory.route)
                parameter("category", category)
            }
            if(response.status == HttpStatusCode.OK) {
                val responseData = response.body<GenericResponse<List<Product>>>().data
                if(responseData != null){
                    emit(Result.success(responseData))
                } else {
                    emit(Result.failure(ProductResult.FETCH_PRODUCTS_FAILED))
                }
            } else {
                emit(Result.failure(ProductResult.FETCH_PRODUCTS_FAILED))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(ProductResult.SERVER_ERROR))
        }
    }

    override fun fetchWishlistedProductsIds(userId: String): Flow<Result<List<String>, ProductResult>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchWishlistedProductsIds.route)
                parameter("userId", userId)
            }
            if(response.status == HttpStatusCode.OK) {
                val responseData = response.body<GenericResponse<List<String>>>().data
                if(responseData != null){
                    emit(Result.success(responseData))
                } else {
                    emit(Result.failure(ProductResult.FETCH_WISHLISTED_PRODUCTS_FAILED))
                }
            } else {
                emit(Result.failure(ProductResult.FETCH_WISHLISTED_PRODUCTS_FAILED))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(ProductResult.SERVER_ERROR))
        }
    }

    override suspend fun addWishlistedProduct(userId: String, productId: String): Result<Unit, ProductResult> {
        return try {
            val response = httpClient.post {
                endPoint(EndPoint.Product.AddWishlistedProduct.route)
                parameter("productId", productId)
                parameter("userId", userId)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(Unit)
            } else {
                Result.failure(ProductResult.ADD_WISHLISTED_PRODUCTS_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }

    override suspend fun removeWishlistedProduct(userId: String, productId: String): Result<Unit, ProductResult>{
        return try {
            val response = httpClient.delete {
                endPoint(EndPoint.Product.RemoveWishlistedProduct.route)
                parameter("productId", productId)
                parameter("userId", userId)
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(Unit)
            } else {
                Result.failure(ProductResult.REMOVE_WISHLISTED_PRODUCTS_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }

    override fun fetchWishlistedProducts(userId: String): Flow<Result<List<Product>, ProductResult>> =
        flow {
            emit(Result.Loading)
            try {
                val response = httpClient.get {
                    endPoint(EndPoint.Product.FetchWishlistedProducts.route)
                    parameter("userId", userId)
                }
                if (response.status == HttpStatusCode.OK) {
                    val responseData = response.body<GenericResponse<List<Product>>>().data
                    if (responseData != null) {
                        emit(Result.success(responseData))
                    } else {
                        emit(Result.failure(ProductResult.FETCH_WISHLISTED_PRODUCTS_FAILED))
                    }
                } else {
                    emit(Result.failure(ProductResult.FETCH_WISHLISTED_PRODUCTS_FAILED))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure(ProductResult.SERVER_ERROR))
            }
        }
    override fun searchProductsByTitle(query: String): Flow<Result<List<Product>, ProductResult>> =
        flow {
            emit(Result.Loading)
            try {
                val response = httpClient.get {
                    endPoint(EndPoint.Product.SearchProductsByName.route)
                    parameter("name", query)
                }
                if (response.status == HttpStatusCode.OK) {
                    val responseData = response.body<GenericResponse<List<Product>>>().data
                    if (responseData != null) {
                        emit(Result.success(responseData))
                    } else {
                        emit(Result.failure(ProductResult.SEARCH_PRODUCTS_BY_TITLE_FAILED))
                    }
                } else {
                    emit(Result.failure(ProductResult.SEARCH_PRODUCTS_BY_TITLE_FAILED))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure(ProductResult.SERVER_ERROR))
            }
        }

    override suspend fun addProductReview(review: ProductReview): Result<Unit, ProductResult> {
        return try {
            val response = httpClient.post {
                endPoint(EndPoint.Product.AddProductReview.route)
                setBody(
                    review
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(Unit)
            } else {
                Result.failure(ProductResult.ADD_PRODUCT_REVIEW_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }
}
package org.zaed.khana.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.source.remote.model.request.ProductRequest
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result

class ProductRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : ProductRemoteDataSource {
    override suspend fun fetchProductById(request: ProductRequest.FetchProductById): Result<Product, ProductResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchProductById.route)
            }
            if(response.status == HttpStatusCode.OK){
                Result.success(response.body<Product>())
            } else {
                Result.failure(ProductResult.FETCH_PRODUCTS_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }

    override suspend fun checkIfIsProductWishlisted(request: ProductRequest.CheckIfIsProductWishlisted): Result<Boolean, ProductResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Product.CheckIfProductIsWishlisted.route)
            }
            if(response.status == HttpStatusCode.OK){
                Result.success(response.body<Boolean>())
            } else {
                Result.failure(ProductResult.CHECK_IF_PRODUCT_IS_WISHLISTED_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }

    //fetch labels displayed above products in home screen as list of strings(e.g.: All, Newest, Popular, Man, Woman,.. etc)
    override fun fetchLabels(): Flow<Result<List<String>, ProductResult>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.post {
                endPoint(EndPoint.Product.FetchLabels.route)
            }
            if(response.status == HttpStatusCode.OK) {
                emit(Result.success(response.body<List<String>>()))
            } else {
                emit(Result.failure(ProductResult.FETCH_LABELS_FAILED))
            }
        } catch(e: Exception) {
            e.printStackTrace()
            emit(Result.failure(ProductResult.SERVER_ERROR))
        }
    }
    //fetch flash sale end time in epoch seconds
    override suspend fun fetchFlashSaleEndTime(): Result<Long, ProductResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchFlashSaleEndTime.route)
            }
            if(response.status == HttpStatusCode.OK){
                Result.success(response.body<Long>())
            } else {
                Result.failure(ProductResult.FETCH_FLASH_SALE_END_TIME_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }

    override fun fetchProductsByLabel(request: ProductRequest.FetchProductsByLabelRequest): Flow<Result<List<Product>, ProductResult>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchProductsByLabel.route)
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                emit(Result.success(response.body<List<Product>>()))
            } else {
                emit(Result.failure(ProductResult.FETCH_PRODUCTS_FAILED))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(ProductResult.SERVER_ERROR))
        }
    }

    override fun fetchWishlistedProductsIds(request: ProductRequest.FetchWishlistedProductsIds): Flow<Result<List<String>, ProductResult>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchWishlistedProductsIds.route)
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                emit(Result.success(response.body<List<String>>()))
            } else {
                emit(Result.failure(ProductResult.FETCH_WISHLISTED_PRODUCTS_FAILED))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(ProductResult.SERVER_ERROR))
        }
    }

    override suspend fun addWishlistedProduct(request: ProductRequest.AddWishlistedProduct): Result<Unit, ProductResult> {
        return try {
            val response = httpClient.post {
                endPoint(EndPoint.Product.AddWishlistedProduct.route)
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK){
                Result.success(Unit)
            } else {
                Result.failure(ProductResult.ADD_WISHLISTED_PRODUCTS_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }

    override suspend fun removeWishlistedProduct(request: ProductRequest.RemoveWishlistedProduct): Result<Unit, ProductResult>{
        return try {
            val response = httpClient.post {
                endPoint(EndPoint.Product.RemoveWishlistedProduct.route)
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK){
                Result.success(Unit)
            } else {
                Result.failure(ProductResult.REMOVE_WISHLISTED_PRODUCTS_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }

    override suspend fun addItemToCart(request: ProductRequest.AddItemToCart): Result<Unit, ProductResult> {
        return try {
            val response = httpClient.post {
                endPoint(EndPoint.Product.AddItemToCart.route)
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK){
                Result.success(Unit)
            } else {
                Result.failure(ProductResult.ADD_ITEM_TO_CART_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(ProductResult.SERVER_ERROR)
        }
    }
}
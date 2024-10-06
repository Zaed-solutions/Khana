package org.zaed.khana.data.source.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.source.remote.model.request.ProductRequest
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.GenericResponse
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result

class ProductRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : ProductRemoteDataSource {
    //fetch labels displayed above products in home screen as list of strings(e.g.: All, Newest, Popular, Man, Woman,.. etc)
    override fun fetchLabels(): Flow<Result<List<String>, ProductResult>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchLabels.route)
            }
            if(response.status == HttpStatusCode.OK) {
                Log.d("ProductRemoteDataSourceImpl", "fetchLabels: response: ${response.body<GenericResponse<List<String>>>().data}")
                emit(Result.success(response.body<GenericResponse<List<String>>>().data ?: emptyList()))
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
                Result.success(response.body<GenericResponse<Long>>().data?: 0)
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
                parameter("label", request.label)
            }
            Log.d("ProductRemoteDataSourceImpl", "fetchProductsByLabel: request: ${response}}")
            if(response.status == HttpStatusCode.OK) {
                Log.d("ProductRemoteDataSourceImpl", "fetchProductsByLabel: response ok: ${response.body<GenericResponse<List<Product>>>().data}")
                emit(Result.success(response.body<GenericResponse<List<Product>>>().data ?: emptyList()))
            } else {
                Log.d("ProductRemoteDataSourceImpl", "fetchProductsByLabel: response not ok: ${response}}")
                emit(Result.failure(ProductResult.FETCH_PRODUCTS_FAILED))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("ProductRemoteDataSourceImpl", "fetchProductsByLabel: ${e.message}")
            emit(Result.failure(ProductResult.SERVER_ERROR))
        }
    }

    override fun fetchWishlistedProductsIds(request: ProductRequest.FetchWishlistedProductsIds): Flow<Result<List<String>, ProductResult>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchWishlistedProductsIds.route)
                parameter("userId", request.userId)
            }
            if(response.status == HttpStatusCode.OK) {
                emit(Result.success(response.body<GenericResponse<List<String>>>().data ?: emptyList()))
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
                parameter("productId", request.productId)
                parameter("userId", request.userId)
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
            val response = httpClient.delete {
                endPoint(EndPoint.Product.RemoveWishlistedProduct.route)
                parameter("productId", request.productId)
                parameter("userId", request.userId)
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
}
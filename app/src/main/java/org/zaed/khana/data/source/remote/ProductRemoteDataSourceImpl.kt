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

class ProductRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : ProductRemoteDataSource {
    //fetch labels displayed above products in home screen as list of strings(e.g.: All, Newest, Popular, Man, Woman,.. etc)
    override fun fetchLabels(): Flow<List<String>> = flow {
        try {
            val response = httpClient.post {
                endPoint(EndPoint.Product.FetchLabels.route)
            }
            if(response.status == HttpStatusCode.OK) {
                emit(response.body<List<String>>())
            }
        }catch(e: Exception){
            e.printStackTrace()
        }
    }

    override fun fetchProductsByLabel(request: ProductRequest.FetchProductsByLabelRequest): Flow<List<Product>> = flow {
        try {
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchProductsByLabel.route)
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                emit(response.body<List<Product>>())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun fetchWishlistedProductsIds(request: ProductRequest.FetchWishlistedProductsIds): Flow<List<String>> = flow {
        try {
            val response = httpClient.get {
                endPoint(EndPoint.Product.FetchWishlistedProductsIds.route)
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK) {
                emit(response.body<List<String>>())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun addWishlistedProduct(request: ProductRequest.AddWishlistedProduct) {
        try {
            val response = httpClient.post {
                endPoint(EndPoint.Product.AddWishlistedProduct.route)
                setBody(request)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun removeWishlistedProduct(request: ProductRequest.RemoveWishlistedProduct) {
        try {
            val response = httpClient.post {
                endPoint(EndPoint.Product.RemoveWishlistedProduct.route)
                setBody(request)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
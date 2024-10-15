package org.zaed.khana.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.GenericResponse
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.ShippingAddressResult
import org.zaed.khana.data.util.Result

class ShippingAddressRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : ShippingAddressRemoteDataSource {
    override fun fetchShippingAddresses(userId: String): Flow<Result<List<ShippingAddress>, ShippingAddressResult>> =
        flow {
            emit(Result.Loading)
            try {
                val request = httpClient.get {
                    endPoint(EndPoint.ShippingAddress.FetchShippingAddresses.route)
                    parameter("userId", userId)
                }
                if (request.status == HttpStatusCode.OK) {
                    val responseData = request.body<GenericResponse<List<ShippingAddress>>>().data
                    if (responseData != null) {
                        emit(Result.Success(responseData))
                    } else {
                        emit(Result.Error(ShippingAddressResult.FETCH_SHIPPING_ADDRESSES_FAILED))
                    }
                } else {
                    emit(Result.Error(ShippingAddressResult.SERVER_ERROR))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(ShippingAddressResult.NETWORK_ERROR))
            }
        }

    override suspend fun addShippingAddress(
        requestBody: ShippingAddress
    ): Result<String, ShippingAddressResult> {
        return try {
            val request = httpClient.post {
                endPoint(EndPoint.ShippingAddress.AddShippingAddress.route)
                setBody(requestBody)
            }
            if (request.status == HttpStatusCode.OK) {
                val responseData = request.body<GenericResponse<String>>().data
                if (responseData != null) {
                    Result.Success(responseData)
                } else {
                    Result.Error(ShippingAddressResult.ADD_SHIPPING_ADDRESS_FAILED)
                }
            } else {
                Result.Error(ShippingAddressResult.ADD_SHIPPING_ADDRESS_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ShippingAddressResult.NETWORK_ERROR)
        }
    }
}
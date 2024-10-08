package org.zaed.khana.data.source

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zaed.khana.data.model.Order
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.source.remote.model.request.CheckoutRequest
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.GenericResponse
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.CheckoutResult
import org.zaed.khana.data.util.Result

class CheckoutRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : CheckoutRemoteDataSource {
    override fun fetchShippingAddresses(userId: String): Flow<Result<List<ShippingAddress>, CheckoutResult>> =
        flow {
            emit(Result.Loading)
            try {
                val request = httpClient.get {
                    endPoint(EndPoint.Checkout.FetchShippingAddresses.route)
                    parameter("userId", userId)
                }
                if (request.status == HttpStatusCode.OK) {
                    val responseData = request.body<GenericResponse<List<ShippingAddress>>>().data
                    if (responseData != null) {
                        emit(Result.Success(responseData))
                    } else {
                        emit(Result.Error(CheckoutResult.FETCH_SHIPPING_ADDRESSES_FAILED))
                    }
                } else {
                    emit(Result.Error(CheckoutResult.SERVER_ERROR))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(CheckoutResult.NETWORK_ERROR))
            }
        }

    override suspend fun addShippingAddress(
        requestBody: CheckoutRequest.AddShippingAddress
    ): Result<Unit, CheckoutResult> {
        return try {
            val request = httpClient.post {
                endPoint(EndPoint.Checkout.AddShippingAddress.route)
                setBody(requestBody)
            }
            if (request.status == HttpStatusCode.OK) {
                Result.Success(Unit)
            } else {
                Result.Error(CheckoutResult.ADD_SHIPPING_ADDRESS_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(CheckoutResult.NETWORK_ERROR)
        }
    }

    override suspend fun placeOrder(order: Order): Result<String, CheckoutResult> {
        return try {
            val request = httpClient.post {
                endPoint(EndPoint.Checkout.AddShippingAddress.route)
                setBody(order)
            }
            if (request.status == HttpStatusCode.OK) {
                val responseData = request.body<GenericResponse<String>>().data
                if (responseData != null) {
                    Result.Success(responseData)
                } else {
                    Result.Error(CheckoutResult.PLACE_ORDER_FAILED)
                }
            } else {
                Result.Error(CheckoutResult.SERVER_ERROR)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(CheckoutResult.NETWORK_ERROR)
        }
    }
}
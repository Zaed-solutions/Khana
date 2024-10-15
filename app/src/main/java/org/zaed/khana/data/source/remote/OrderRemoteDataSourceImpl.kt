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
import org.zaed.khana.data.model.Order
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.GenericResponse
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.OrderResult
import org.zaed.khana.data.util.Result

class OrderRemoteDataSourceImpl (
    private val httpClient: HttpClient
): OrderRemoteDataSource {
    override suspend fun placeOrder(order: Order): Result<String, OrderResult> {
        return try {
            val request = httpClient.post {
                endPoint(EndPoint.Order.PlaceOrder.route)
                setBody(order)
            }
            if (request.status == HttpStatusCode.OK) {
                val responseData = request.body<GenericResponse<String>>().data
                if (responseData != null) {
                    Result.Success(responseData)
                } else {
                    Result.Error(OrderResult.PLACE_ORDER_FAILED)
                }
            } else {
                Result.Error(OrderResult.SERVER_ERROR)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(OrderResult.NETWORK_ERROR)
        }
    }

    override fun fetchUserOrders(userId: String): Flow<Result<List<Order>, OrderResult>> = flow {
        emit(Result.Loading)
        try {
            val request = httpClient.get {
                endPoint(EndPoint.Order.FetchUserOrders.route)
                parameter("userId", userId)
            }
            if (request.status == HttpStatusCode.OK) {
                val responseData = request.body<GenericResponse<List<Order>>>().data
                if (responseData != null) {
                    emit(Result.Success(responseData))
                } else {
                    emit(Result.Error(OrderResult.FETCH_USER_ORDERS_FAILED))
                }
            } else {
                emit(Result.Error(OrderResult.SERVER_ERROR))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(OrderResult.NETWORK_ERROR))
        }
    }
}
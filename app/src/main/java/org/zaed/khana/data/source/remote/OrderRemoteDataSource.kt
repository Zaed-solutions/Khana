package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Order
import org.zaed.khana.data.util.OrderResult
import org.zaed.khana.data.util.Result

interface OrderRemoteDataSource {
    suspend fun placeOrder(order: Order): Result<String, OrderResult>
    fun fetchUserOrders(userId: String): Flow<Result<List<Order>, OrderResult>>
}
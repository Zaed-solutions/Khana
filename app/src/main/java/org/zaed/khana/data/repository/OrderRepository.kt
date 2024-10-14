package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Order
import org.zaed.khana.data.util.OrderResult
import org.zaed.khana.data.util.Result

interface OrderRepository {
    fun fetchUserOrders(userId: String): Flow<Result<List<Order>, OrderResult>>
    suspend fun placeOrder(order: Order): Result<String, OrderResult>
}
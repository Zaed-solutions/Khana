package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Order
import org.zaed.khana.data.source.remote.OrderRemoteDataSource
import org.zaed.khana.data.util.OrderResult
import org.zaed.khana.data.util.Result

class OrderRepositoryImpl(
    private val orderRemoteDataSource: OrderRemoteDataSource
) : OrderRepository {
    override fun fetchUserOrders(userId: String): Flow<Result<List<Order>, OrderResult>> {
        return orderRemoteDataSource.fetchUserOrders(userId)
    }

    override suspend fun placeOrder(order: Order): Result<String, OrderResult> {
        return orderRemoteDataSource.placeOrder(order)
    }

    override suspend fun fetchOrderById(orderId: String): Result<Order, OrderResult> {
        return orderRemoteDataSource.fetchOrderById(orderId)
    }
}
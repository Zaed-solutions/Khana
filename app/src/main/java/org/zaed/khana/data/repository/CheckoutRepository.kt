package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Order
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.util.CheckoutResult
import org.zaed.khana.data.util.Result

interface CheckoutRepository {
    fun fetchShippingAddresses(userId: String): Flow<Result<List<ShippingAddress>, CheckoutResult>>
    suspend fun addShippingAddress(
        shippingAddress: ShippingAddress
    ): Result<String, CheckoutResult>

    suspend fun placeOrder(order: Order): Result<String, CheckoutResult>
}
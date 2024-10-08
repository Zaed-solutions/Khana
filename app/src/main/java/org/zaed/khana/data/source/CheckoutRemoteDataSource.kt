package org.zaed.khana.data.source

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Order
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.source.remote.model.request.CheckoutRequest
import org.zaed.khana.data.util.CheckoutResult
import org.zaed.khana.data.util.Result

interface CheckoutRemoteDataSource {
    fun fetchShippingAddresses(userId: String): Flow<Result<List<ShippingAddress>, CheckoutResult>>
    suspend fun addShippingAddress(
        requestBody: CheckoutRequest.AddShippingAddress
    ): Result<Unit, CheckoutResult>

    suspend fun placeOrder(order: Order): Result<String, CheckoutResult>
}
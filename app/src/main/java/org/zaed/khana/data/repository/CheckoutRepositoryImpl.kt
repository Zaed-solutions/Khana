package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Order
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.source.CheckoutRemoteDataSource
import org.zaed.khana.data.source.remote.model.request.CheckoutRequest
import org.zaed.khana.data.util.CheckoutResult
import org.zaed.khana.data.util.Result

class CheckoutRepositoryImpl(
    private val checkoutRemoteSource: CheckoutRemoteDataSource
) : CheckoutRepository {
    override fun fetchShippingAddresses(userId: String): Flow<Result<List<ShippingAddress>, CheckoutResult>> {
        return checkoutRemoteSource.fetchShippingAddresses(userId)
    }

    override suspend fun addShippingAddress(
        userId: String,
        shippingAddress: ShippingAddress
    ): Result<Unit, CheckoutResult> {
        val requestBody = CheckoutRequest.AddShippingAddress(userId, shippingAddress)
        return checkoutRemoteSource.addShippingAddress(requestBody)
    }

    override suspend fun placeOrder(order: Order): Result<String, CheckoutResult> {
        return checkoutRemoteSource.placeOrder(order)
    }
}
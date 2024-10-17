package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.util.ShippingAddressResult
import org.zaed.khana.data.util.Result

interface ShippingAddressRepository {
    fun fetchShippingAddresses(userId: String): Flow<Result<List<ShippingAddress>, ShippingAddressResult>>
    suspend fun addShippingAddress(
        shippingAddress: ShippingAddress
    ): Result<String, ShippingAddressResult>


}
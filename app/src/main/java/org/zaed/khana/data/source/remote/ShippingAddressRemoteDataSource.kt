package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.util.ShippingAddressResult
import org.zaed.khana.data.util.Result

interface ShippingAddressRemoteDataSource {
    fun fetchShippingAddresses(userId: String): Flow<Result<List<ShippingAddress>, ShippingAddressResult>>
    suspend fun addShippingAddress(
        requestBody: ShippingAddress
    ): Result<String, ShippingAddressResult>

}
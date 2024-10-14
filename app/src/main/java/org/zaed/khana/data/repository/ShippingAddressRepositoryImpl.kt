package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.source.remote.ShippingAddressRemoteDataSource
import org.zaed.khana.data.util.ShippingAddressResult
import org.zaed.khana.data.util.Result

class ShippingAddressRepositoryImpl(
    private val checkoutRemoteSource: ShippingAddressRemoteDataSource
) : ShippingAddressRepository {
    override fun fetchShippingAddresses(userId: String): Flow<Result<List<ShippingAddress>, ShippingAddressResult>> {
        return checkoutRemoteSource.fetchShippingAddresses(userId)
    }

    override suspend fun addShippingAddress(
        shippingAddress: ShippingAddress
    ): Result<String, ShippingAddressResult> {
        return checkoutRemoteSource.addShippingAddress(shippingAddress)
    }


}
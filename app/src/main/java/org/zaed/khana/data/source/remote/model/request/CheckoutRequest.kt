package org.zaed.khana.data.source.remote.model.request

import org.zaed.khana.data.model.ShippingAddress

sealed interface CheckoutRequest {
    data class AddShippingAddress(
        val userId: String,
        val shippingAddress: ShippingAddress
    ): CheckoutRequest
}
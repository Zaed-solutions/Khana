package org.zaed.khana.presentation.checkout

import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.model.ShippingType

data class CheckoutUiState(
    val currentUserId: String = "",
    val shippingAddresses: List<ShippingAddress> = emptyList(),
    val selectedShippingAddress: ShippingAddress = ShippingAddress(),
    val selectedShippingType: ShippingType = ShippingType.ECONOMY,
)
package org.zaed.khana.presentation.checkout

import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.model.ShippingType
import org.zaed.khana.data.model.User

data class CheckoutUiState(
    val currentUser: User = User(),
    val shippingAddresses: List<ShippingAddress> = emptyList(),
    val cartItems: List<CartItem> = emptyList(),
    val selectedShippingAddress: ShippingAddress = ShippingAddress(),
    val selectedShippingType: ShippingType = ShippingType.ECONOMY,
)
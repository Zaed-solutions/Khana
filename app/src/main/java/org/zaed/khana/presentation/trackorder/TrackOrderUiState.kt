package org.zaed.khana.presentation.trackorder

import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Order
import org.zaed.khana.data.model.User

data class TrackOrderUiState(
    val currentUser: User = User(),
    val isLoadingCartItems: Boolean = true,
    val isLoadingOrderDetails: Boolean = true,
    val order: Order = Order(),
    val cartItem: CartItem = CartItem(),
)

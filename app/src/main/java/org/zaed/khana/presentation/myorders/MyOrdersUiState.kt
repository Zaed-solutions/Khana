package org.zaed.khana.presentation.myorders

import org.zaed.khana.data.model.Order
import org.zaed.khana.data.model.OrderedCartItem
import org.zaed.khana.data.model.User


data class MyOrdersUiState(
    val currentUser: User = User(),
    val isLoading: Boolean = true,
    val orders: List<Order> = emptyList(),
    val displayedItems: List<OrderedCartItem> = emptyList()
)

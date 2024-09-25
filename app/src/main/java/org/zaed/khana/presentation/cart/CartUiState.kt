package org.zaed.khana.presentation.cart

import org.zaed.khana.data.model.CartItem

data class CartUiState(
    val currentUserId: String = "",
    val cartItems: List<CartItem> = emptyList(),
    val discountPercentage: Float = 0f,
    val deliveryFee: Float = 0f
){
    val subTotalPrice: Float
        get() = cartItems.sumOf { (it.productBasePrice * it.quantity).toDouble()}.toFloat()
}
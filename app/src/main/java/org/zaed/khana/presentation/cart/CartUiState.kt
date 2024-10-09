package org.zaed.khana.presentation.cart

import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.User

data class CartUiState(
    val currentUser: User = User(),
    val cartItems: List<CartItem> = emptyList(),
    val discountPercentage: Float = 0f,
    val deliveryFee: Float = 0f
){
    val subTotalPrice: Float
        get() = cartItems.sumOf { (it.productBasePrice * it.quantity).toDouble()}.toFloat()
}
package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String = "",
    val userId: String = "",
    val cartItems: List<CartItem> = emptyList(),
    val shippingAddress: ShippingAddress = ShippingAddress(),
    val shippingType: String = ShippingType.ECONOMY.title,
    val paymentStatus: String = PaymentStatus.NOT_SET.name,
    val orderStatus: String = OrderStatus.AWAITING_CONFIRMATION.name,
    val totalPrice: Float = 0f,
)

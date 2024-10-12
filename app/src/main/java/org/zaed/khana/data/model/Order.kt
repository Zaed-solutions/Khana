package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String = "",
    val userId: String = "",
    val cartItemsIds: List<String> = emptyList(),
    val shippingAddress: ShippingAddress = ShippingAddress(),
    val shippingType: String = ShippingType.ECONOMY.title,
    val paymentStatus: String = PaymentStatus.NOT_SET.name,
    val orderStatus: String = OrderStatus.PENDING.name,
    val totalPrice: Float = 0f,
)

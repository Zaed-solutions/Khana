package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String = "",
    val userId: String = "",
    val cartItemsIds: List<String> = emptyList(),
    val shippingAddress: ShippingAddress = ShippingAddress(),
    val shippingType: ShippingType = ShippingType.ECONOMY,
    val paymentStatus: PaymentStatus = PaymentStatus.NOT_SET,
    val orderStatus: OrderStatus = OrderStatus.PENDING,
)

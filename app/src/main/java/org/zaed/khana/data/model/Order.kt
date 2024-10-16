package org.zaed.khana.data.model

import kotlinx.datetime.Clock
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
    val trackingId: String = "",
    val expectedDeliveryEpochSeconds: Long = 0,
    val createdAtEpochSeconds: Long = Clock.System.now().epochSeconds,
    val cancelledEpochSeconds: Long = 0,
    val confirmedEpochSeconds: Long = 0,
    val shippedEpochSeconds: Long = 0,
    val deliveredEpochSeconds: Long = 0,
)

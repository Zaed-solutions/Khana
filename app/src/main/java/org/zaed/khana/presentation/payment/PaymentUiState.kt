package org.zaed.khana.presentation.payment

import org.zaed.khana.data.model.User

data class PaymentUiState(
    val currentUser: User = User(),
    val orderId: String = "",
    val selectedPaymentMethod: String = PaymentMethods.CASH_ON_DELIVERY.name,
    val isPaymentConfirmed: Boolean = false
)

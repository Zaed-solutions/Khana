package org.zaed.khana.presentation.payment

sealed interface PaymentUiAction {
    data object OnBackPressed: PaymentUiAction
    data object OnContinueClicked: PaymentUiAction
    data class OnPaymentMethodSelected(val paymentMethod: String): PaymentUiAction
}
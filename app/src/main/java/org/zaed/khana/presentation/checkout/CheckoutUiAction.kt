package org.zaed.khana.presentation.checkout

import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.model.ShippingType

sealed interface CheckoutUiAction {
    data object OnBackPressed : CheckoutUiAction
    data object OnContinueToPayment : CheckoutUiAction
    data class OnChangeShippingAddress(val newAddress: ShippingAddress) : CheckoutUiAction
    data class OnAddShippingAddress(val newAddress: ShippingAddress) : CheckoutUiAction
    data class OnChangeShippingType(val newType: ShippingType) : CheckoutUiAction

}
package org.zaed.khana.presentation.cart

sealed interface CartUiAction{
    data object OnBackPressed: CartUiAction
    data class OnIncrementItemQuantity(val productId: String): CartUiAction
    data class OnDecrementItemQuantity(val productId: String): CartUiAction
    data class OnRemoveItemFromCart(val productId: String): CartUiAction
    data class OnApplyPromoCode(val code: String): CartUiAction
    data object OnProceedToCheckout: CartUiAction
}
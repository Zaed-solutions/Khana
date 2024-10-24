package org.zaed.khana.presentation.cart

sealed interface CartUiAction{
    data object OnBackPressed: CartUiAction
    data class OnIncrementItemQuantity(val itemId: String): CartUiAction
    data class OnDecrementItemQuantity(val itemId: String): CartUiAction
    data class OnRemoveItemFromCart(val itemId: String): CartUiAction
    data class OnApplyPromoCode(val code: String): CartUiAction
    data object OnProceedToCheckout: CartUiAction
    data object OnViewCoupons: CartUiAction
}
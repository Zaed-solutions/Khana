package org.zaed.khana.presentation.checkout

sealed interface CheckoutUiAction {
    data object OnBackPressed : CheckoutUiAction
}
package org.zaed.khana.presentation.category

sealed interface CategoryUiAction {
    data object OnBackPressed : CategoryUiAction
    data class OnWishlistClicked(val productId: String) : CategoryUiAction
    data class OnProductClicked(val productId: String) : CategoryUiAction
}
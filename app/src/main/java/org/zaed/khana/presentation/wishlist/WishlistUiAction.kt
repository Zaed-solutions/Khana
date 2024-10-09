package org.zaed.khana.presentation.wishlist

sealed interface WishlistUiAction {
    data object OnBackPressed : WishlistUiAction
    data class OnCategoryClicked(val category: String) : WishlistUiAction
    data class OnProductClicked(val productId: String) : WishlistUiAction
    data class OnRemoverWishlistedProduct(val productId: String) : WishlistUiAction

}
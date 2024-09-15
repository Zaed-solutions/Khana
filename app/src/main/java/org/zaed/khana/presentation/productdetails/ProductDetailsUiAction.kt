package org.zaed.khana.presentation.productdetails

interface ProductDetailsUiAction {
    data object OnBackPressed: ProductDetailsUiAction
    data object OnWishlistProduct: ProductDetailsUiAction
    data class OnSelectSize(val size: String): ProductDetailsUiAction
    data class OnSelectColor(val hexColor: String): ProductDetailsUiAction
    data object OnAddToCartClicked: ProductDetailsUiAction
}
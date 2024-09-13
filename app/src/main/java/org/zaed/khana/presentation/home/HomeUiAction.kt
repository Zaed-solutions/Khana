package org.zaed.khana.presentation.home

sealed interface HomeUiAction {
    data object OnNotificationButtonClicked: HomeUiAction
    data object OnFiltersButtonClicked: HomeUiAction
    data object OnSearchClicked: HomeUiAction
    data class OnSelectLabel(val label: String): HomeUiAction
    data class OnProductClicked(val productId: String): HomeUiAction
    data class OnWishlistProduct(val productId: String): HomeUiAction

}
package org.zaed.khana.presentation.home

sealed interface HomeUiAction {
    data object OnNotificationButtonClicked: HomeUiAction
    data object OnFiltersButtonClicked: HomeUiAction
    data object OnSearchClicked: HomeUiAction
    data class OnUpdateSortedByOption(val sortedByOption: String): HomeUiAction
    data class OnProductClicked(val productId: String): HomeUiAction
    data class OnWishlistProduct(val productId: String): HomeUiAction
    data object OnBrowseOffersClicked: HomeUiAction

}
package org.zaed.khana.presentation.home

sealed interface HomeUiAction {
    data object OnNotificationButtonClicked: HomeUiAction
    data object OnFiltersButtonClicked: HomeUiAction
    data class OnSearchQueryChanged(val newQuery: String): HomeUiAction
    data class OnSearch(val searchTerm: String): HomeUiAction
    data class OnChangeSearchingStatus(val isSearching: Boolean): HomeUiAction
    data class OnSelectLabel(val label: String): HomeUiAction
    data class OnProductClicked(val productId: String): HomeUiAction
    data class OnWishlistProduct(val productId: String): HomeUiAction

}
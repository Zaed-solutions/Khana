package org.zaed.khana.presentation.search

sealed interface SearchUiAction {
    data object OnBackPressed : SearchUiAction
    data class OnSearchQueryChanged(val query: String) : SearchUiAction
    data class OnProductClicked(val productId: String) : SearchUiAction
    data class OnWishlistProductClicked(val productId: String) : SearchUiAction
    data class OnRecentSearchClicked(val query: String) : SearchUiAction
    data class OnDeleteRecentSearchClicked(val query: String) : SearchUiAction
    data class OnAddRecentSearchItem(val query: String) : SearchUiAction
    data object OnClearRecentSearchesClicked : SearchUiAction


}
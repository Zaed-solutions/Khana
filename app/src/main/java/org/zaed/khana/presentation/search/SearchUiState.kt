package org.zaed.khana.presentation.search

import org.zaed.khana.data.model.Product

data class SearchUiState(
    val currentUserId: String = "",
    val products: List<Product> = emptyList(),
    val wishlistedProductsIds: List<String> = emptyList(),
    val recentSearches: List<String> = emptyList(),
)
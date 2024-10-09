package org.zaed.khana.presentation.search

import org.zaed.khana.data.model.Product
import org.zaed.khana.data.model.User

data class SearchUiState(
    val currentUser: User = User(),
    val products: List<Product> = emptyList(),
    val wishlistedProductsIds: List<String> = emptyList(),
    val recentSearches: List<String> = emptyList(),
)
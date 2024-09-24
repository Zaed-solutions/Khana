package org.zaed.khana.presentation.wishlist

import org.zaed.khana.data.model.Product

data class WishlistUiState(
    val currentUserId: String = "",
    val wishlistedProducts: List<Product> = emptyList(),
    val displayedProducts: List<Product> = emptyList(),
    val categories: Set<String> = emptySet(),
    val selectedCategory: String = "",
)

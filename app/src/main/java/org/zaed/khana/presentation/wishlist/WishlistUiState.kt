package org.zaed.khana.presentation.wishlist

import org.zaed.khana.data.model.Product
import org.zaed.khana.data.model.User

data class WishlistUiState(
    val currentUser: User = User(),
    val wishlistedProducts: List<Product> = emptyList(),
    val displayedProducts: List<Product> = emptyList(),
    val categories: Set<String> = emptySet(),
    val selectedCategory: String = "",
)

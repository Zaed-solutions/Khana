package org.zaed.khana.presentation.category

import org.zaed.khana.data.model.Product
import org.zaed.khana.data.model.User

data class CategoryUiState(
    val currentUser: User = User(),
    val products: List<Product> = emptyList(),
    val category: String = "",
    val wishlistedProductsIds: List<String> = emptyList(),
)

package org.zaed.khana.presentation.home

import kotlinx.datetime.Clock
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.data.model.Category
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.model.ProductFilter
import org.zaed.khana.data.model.User

data class HomeUiState(
    val currentUser: User = User(),
    val isLoading: Boolean = true,
    val ads: List<Advertisement> = emptyList(),
    val categories: List<Category> = emptyList(),
    val flashSaleEndsAtEpochSeconds: Long = Clock.System.now().epochSeconds,
    val filter : ProductFilter = ProductFilter(),
    val sortedByOptions: List<String> = emptyList(),
    val products: List<Product> = emptyList(),
    val wishlistedProductsIds: List<String> = emptyList(),
)

package org.zaed.khana.presentation.home

import kotlinx.datetime.Clock
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.data.model.Category
import org.zaed.khana.data.model.Product

data class HomeUiState(
    val currentUserId: String = "",
    val hasNewNotification: Boolean= false,
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val ads: List<Advertisement> = emptyList(),
    val categories: List<Category> = emptyList(),
    val flashSaleEndsAtEpochSeconds: Long = Clock.System.now().epochSeconds,
    val labels: List<String> = emptyList(),
    val selectedLabel: String = "All",
    val products: List<Product> = emptyList(),
    val wishlistedProductsIds: List<String> = emptyList(),
)

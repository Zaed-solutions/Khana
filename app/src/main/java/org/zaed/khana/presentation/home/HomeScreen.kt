package org.zaed.khana.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.data.model.Category
import org.zaed.khana.data.model.Product
import org.zaed.khana.presentation.home.components.AdvertisementSection
import org.zaed.khana.presentation.home.components.CategoriesSection
import org.zaed.khana.presentation.home.components.FlashSaleSection
import org.zaed.khana.presentation.home.components.LabelFilterSection
import org.zaed.khana.presentation.home.components.LocationAndNotificationsSection
import org.zaed.khana.presentation.home.components.ProductItems
import org.zaed.khana.presentation.home.components.SearchAndFiltersSection

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        modifier = modifier,
        hasNewNotification = state.hasNewNotification,
        ads = state.ads,
        categories = state.categories,
        flashSaleEndsAtEpochSeconds = state.flashSaleEndsAtEpochSeconds,
        labels = state.labels,
        selectedLabel = state.selectedLabel,
        products = state.products,
        wishlistedProducts = state.wishlistedProductsIds,
        onAction = { action ->
            when(action){
                HomeUiAction.OnFiltersButtonClicked -> {
                    TODO("navigate to filters screen")
                }
                HomeUiAction.OnNotificationButtonClicked -> {
                    TODO("navigate to notifications screen")
                }
                is HomeUiAction.OnProductClicked -> {
                    TODO("navigate to product details")
                }
                is HomeUiAction.OnSearchClicked -> {
                    TODO("navigate to search screen")
                }
                else -> viewModel.handleUiAction(action)
            }
        }
    )
}

@Composable
private fun HomeContent(
    hasNewNotification: Boolean,
    ads: List<Advertisement>,
    categories: List<Category>,
    flashSaleEndsAtEpochSeconds: Long,
    labels: List<String>,
    selectedLabel: String,
    products: List<Product>,
    wishlistedProducts: List<String>,
    onAction: (HomeUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(all = 16.dp)
        ) {
            item {
                LocationAndNotificationsSection(
                    onNotificationsButtonClicked = {
                        onAction(HomeUiAction.OnNotificationButtonClicked)
                    },
                    hasNewNotification = hasNewNotification
                )
            }
            item {
                SearchAndFiltersSection(
                    onFiltersButtonClicked = { onAction(HomeUiAction.OnFiltersButtonClicked) },
                    onChangeSearchingStatus = { if(it) onAction(HomeUiAction.OnSearchClicked) }
                )
            }
            //ads pager
            item {
                AdvertisementSection(ads = ads)
            }
            //categories
            item {
                CategoriesSection(categories)
            }
            //flash sale (optional)
            item {
                FlashSaleSection(flashSaleEndsAtEpochSeconds)
            }
            //items
            item {
                LabelFilterSection(
                    labels = labels,
                    selectedLabel = selectedLabel,
                    onSelectLabel = { onAction(HomeUiAction.OnSelectLabel(it)) }
                )
            }
            item {
                ProductItems(
                    products = products,
                    wishlistedProducts = wishlistedProducts,
                    onProductClicked = { onAction(HomeUiAction.OnProductClicked(it)) },
                    onWishlistProduct = { onAction(HomeUiAction.OnWishlistProduct(it)) },
                    screenWidth = screenWidth.value.toInt()
                )
            }
        }
    }
}
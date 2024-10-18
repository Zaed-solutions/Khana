package org.zaed.khana.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.Clock
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.data.model.Advertisement
import org.zaed.khana.data.model.Category
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.model.ProductFilter
import org.zaed.khana.presentation.home.components.AdvertisementSection
import org.zaed.khana.presentation.home.components.CategoriesSection
import org.zaed.khana.presentation.home.components.FlashSaleSection
import org.zaed.khana.presentation.home.components.LocationAndNotificationsSection
import org.zaed.khana.presentation.home.components.ProductItems
import org.zaed.khana.presentation.home.components.SearchAndFiltersSection
import org.zaed.khana.presentation.home.components.SortedByFilterSection
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    productFilter: ProductFilter,
    onNavigateToFilterScreen: () -> Unit,
    onNavigateToNotificationsScreen: () -> Unit,
    onNavigateToProductDetailsScreen: (String) -> Unit,
    onNavigateToSearchScreen: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.init(productFilter)
    }
    HomeContent(
        modifier = modifier,
        hasNewNotification = state.hasNewNotification,
        ads = state.ads,
        categories = state.categories,
        flashSaleEndsAtEpochSeconds = state.flashSaleEndsAtEpochSeconds,
        sortedByOptions = state.sorterByOptions,
        selectedLabel = state.filter.sortedBy,
        products = state.products,
        wishlistedProducts = state.wishlistedProductsIds,
        onAction = { action ->
            when (action) {
                HomeUiAction.OnFiltersButtonClicked -> onNavigateToFilterScreen()
                HomeUiAction.OnNotificationButtonClicked -> onNavigateToNotificationsScreen()
                is HomeUiAction.OnProductClicked -> onNavigateToProductDetailsScreen(action.productId)
                is HomeUiAction.OnSearchClicked -> onNavigateToSearchScreen()
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
    sortedByOptions: List<String>,
    selectedLabel: String,
    products: List<Product>,
    wishlistedProducts: List<String>,
    onAction: (HomeUiAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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
                    onChangeSearchingStatus = { if (it) onAction(HomeUiAction.OnSearchClicked) }
                )
            }
            item {
                AdvertisementSection(ads = ads)
            }
            item {
                CategoriesSection(categories)
            }
            item {
                FlashSaleSection(flashSaleEndsAtEpochSeconds)
            }
            item {
                SortedByFilterSection(
                    sortedByOption = sortedByOptions,
                    selectedOption = selectedLabel,
                    onSelectOption = { onAction(HomeUiAction.OnUpdateSortedByOption(it)) }
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    val ads = listOf(
        Advertisement(title = "Test Advertisement 1"),
        Advertisement(title = "Test Advertisement 2")
    )
    val categories = listOf(
        Category("", "Jacket"),
        Category("", "Shirt"),
        Category("", "Pants"),
        Category("", "Shoes"),
    )
    val products = listOf(
        Product(name = "Product 1", rating = 4.3f, basePrice = 130f),
        Product(name = "Product 2", rating = 1.6f, basePrice = 236f),
        Product(name = "Product 3", rating = 4.1f, basePrice = 99.99f),
        Product(name = "Product 4", rating = 3.2f, basePrice = 350f),
    )
    KhanaTheme {
        HomeContent(
            hasNewNotification = true,
            ads = ads,
            categories = categories,
            flashSaleEndsAtEpochSeconds = Clock.System.now().epochSeconds + 30000,
            sortedByOptions = listOf("All", "Newest", "Popular", "Men", "Women"),
            selectedLabel = "All",
            products = products,
            wishlistedProducts = emptyList(),
            onAction = {}
        )
    }
}
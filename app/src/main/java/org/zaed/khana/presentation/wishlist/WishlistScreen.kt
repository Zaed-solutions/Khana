package org.zaed.khana.presentation.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.Product
import org.zaed.khana.presentation.home.components.SortedByFilterSection
import org.zaed.khana.presentation.home.components.ProductItem
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun WishlistScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onNavigateToProductDetails: (String) -> Unit,
    viewModel: WishlistViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    WishlistScreenContent(
        categories = state.categories.toList(),
        products = state.displayedProducts,
        selectedCategory = state.selectedCategory,
        onAction = { action ->
            when (action) {
                WishlistUiAction.OnBackPressed -> onBackPressed()
                is WishlistUiAction.OnProductClicked -> onNavigateToProductDetails(action.productId)
                else -> viewModel.handleUiAction(action)
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WishlistScreenContent(
    modifier: Modifier = Modifier,
    categories: List<String>,
    products: List<Product>,
    selectedCategory: String,
    onAction: (WishlistUiAction) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.my_wishlist)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(WishlistUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                })
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            SortedByFilterSection(
                sortedByOption = categories,
                selectedOption = selectedCategory,
                onSelectOption = { category ->
                    onAction(WishlistUiAction.OnCategoryClicked(category))
                }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(products.size) { index ->
                    val product = products[index]
                    ProductItem(
                        productName = product.name,
                        productThumbnailImageLink = product.thumbnailImageLink,
                        productRating = product.rating,
                        productPrice = product.basePrice,
                        isWishlisted = true,
                        onWishlistProduct = {
                            onAction(
                                WishlistUiAction.OnRemoverWishlistedProduct(
                                    product.id
                                )
                            )
                        },
                        onProductClicked = { onAction(WishlistUiAction.OnProductClicked(product.id)) },
                        screenWidth = screenWidth.value.toInt()
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun WishlistScreenContentPreview() {
    val categories = listOf("All", "Jacket", "Shirt", "Pant", "T-Shirt")
    val products = (1..8).map { index ->
        Product(
            name = "Product $index",
            rating = (0..5).random().toFloat(),
            basePrice = (100..300).random().toFloat()
        )
    }
    KhanaTheme {
        WishlistScreenContent(
            categories = categories,
            products = products,
            selectedCategory = "All"
        ) {}
    }
}

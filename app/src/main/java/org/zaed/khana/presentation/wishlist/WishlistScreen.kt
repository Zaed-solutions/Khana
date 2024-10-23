package org.zaed.khana.presentation.wishlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.Product
import org.zaed.khana.presentation.home.components.SortedByFilterSection
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.wishlist.components.WishlistedProductsList

@Composable
fun WishlistScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onNavigateToProductDetails: (String) -> Unit,
    viewModel: WishlistViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    WishlistScreenContent(
        modifier = modifier,
        categories = state.categories.toList(),
        products = state.displayedProducts,
        selectedCategory = state.selectedCategory,
        isLoading = state.isLoading,
        onAction = { action ->
            when (action) {
                WishlistUiAction.OnBackPressed -> onBackPressed()
                is WishlistUiAction.OnProductClicked -> onNavigateToProductDetails(action.productId)
                else -> viewModel.handleUiAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WishlistScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    categories: List<String>,
    products: List<Product>,
    selectedCategory: String,
    onAction: (WishlistUiAction) -> Unit
) {
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
            AnimatedVisibility(visible = categories.size > 1) {
                SortedByFilterSection(
                    isLoading = isLoading,
                    sortedByOption = categories,
                    selectedOption = selectedCategory,
                    onSelectOption = { category ->
                        onAction(WishlistUiAction.OnCategoryClicked(category))
                    }
                )
            }
            WishlistedProductsList(
                products = products,
                isLoading = isLoading,
                onWishlistProduct = { productId ->
                    onAction(
                        WishlistUiAction.OnRemoverWishlistedProduct(
                            productId
                        )
                    )
                },
                onProductClicked = { productId ->
                    onAction(WishlistUiAction.OnProductClicked(productId))
                }
            )
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
            isLoading = false,
            selectedCategory = "All"
        ) {}
    }
}

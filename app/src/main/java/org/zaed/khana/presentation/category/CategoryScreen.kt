package org.zaed.khana.presentation.category

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.data.model.Product
import org.zaed.khana.presentation.category.components.CategoryProducts

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = koinViewModel(),
    category: String,
    onBackPressed: () -> Unit,
    onNavigateToProductDetails: (String) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.init(category)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    CategoryScreenContent(
        modifier = modifier,
        category = state.category,
        isLoading = state.isLoading,
        wishlistedProductsIds = state.wishlistedProductsIds,
        products = state.products
    ) { action ->
        when(action) {
            is CategoryUiAction.OnBackPressed ->  { onBackPressed() }
            is CategoryUiAction.OnProductClicked -> { onNavigateToProductDetails(action.productId) }
            else -> viewModel.handleUiAction(action)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryScreenContent(
    modifier: Modifier = Modifier,
    category: String,
    isLoading: Boolean,
    wishlistedProductsIds: List<String>,
    products: List<Product>,
    onAction: (CategoryUiAction) -> Unit
) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    OutlinedIconButton(onClick = { onAction(CategoryUiAction.OnBackPressed) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        CategoryProducts(
            modifier = Modifier.padding(paddingValues),
            products = products,
            isLoading = isLoading,
            wishlistedProductsIds = wishlistedProductsIds,
            onWishlistProduct = { productId ->
                onAction(CategoryUiAction.OnWishlistClicked(productId))
            },
            onProductClicked = { productId ->
                onAction(CategoryUiAction.OnProductClicked(productId))
            }
        )
    }
}


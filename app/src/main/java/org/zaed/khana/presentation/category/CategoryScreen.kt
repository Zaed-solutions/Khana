package org.zaed.khana.presentation.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.data.model.Product
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.presentation.home.components.ProductItem
import org.zaed.khana.presentation.home.components.ProductItems

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
    wishlistedProductsIds: List<String>,
    products: List<Product>,
    onAction: (CategoryUiAction) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = category)
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(CategoryUiAction.OnBackPressed) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(products.size){ index ->
                val product = products[index]
                ProductItem(
                    productName = product.name,
                    productThumbnailImageLink = product.thumbnailImageLink,
                    productRating = product.rating,
                    productPrice = product.basePrice ,
                    isWishlisted = wishlistedProductsIds.contains(product.id),
                    onWishlistProduct = { onAction(CategoryUiAction.OnWishlistClicked(product.id)) },
                    onProductClicked = { onAction(CategoryUiAction.OnProductClicked(product.id)) },
                    screenWidth = screenWidth.value.toInt()
                )
            }
        }
    }
}
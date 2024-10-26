package org.zaed.khana.presentation.search.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.Product
import org.zaed.khana.presentation.components.EmptySearchResult
import org.zaed.khana.presentation.home.components.ProductItem
import org.zaed.khana.presentation.home.components.ProductItemShimmer
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResultList(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    searchQuery: String = "",
    products: List<Product>,
    onProductClicked: (String) -> Unit,
    onWishlistProduct: (String) -> Unit,
    wishlistedProducts: List<String>,
) {
    Crossfade(targetState = isLoading to products, label = "Search Result") { state ->
        when {
            state.first -> {
                SearchResultShimmer(
                    modifier = modifier,
                    searchQuery = searchQuery
                )
            }
            
            state.second.isEmpty() -> {
                EmptySearchResult(modifier)
            }

            else -> {
                SearchResultContent(
                    modifier = modifier,
                    searchQuery = searchQuery,
                    products = products,
                    wishlistedProducts = wishlistedProducts,
                    onWishlistProduct = onWishlistProduct,
                    onProductClicked = onProductClicked
                )
            }
        }
    }
}

@Composable
private fun SearchResultContent(
    modifier: Modifier,
    searchQuery: String,
    products: List<Product>,
    wishlistedProducts: List<String>,
    onWishlistProduct: (String) -> Unit,
    onProductClicked: (String) -> Unit
) {
    val state = rememberLazyGridState()
    LazyVerticalGrid(
        state = state,
        columns = GridCells.Adaptive(160.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        item(
            span = {
                GridItemSpan(this.maxLineSpan)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Result for \"$searchQuery\"",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(text = "${products.size} found", style = MaterialTheme.typography.titleMedium)
            }
        }
        items(products.size) { index ->
            val product = products[index]
            ProductItem(
                modifier = Modifier
                    .animateItem()
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                productName = product.name,
                productThumbnailImageLink = product.thumbnailImageLink,
                productRating = product.rating,
                productPrice = product.basePrice,
                isWishlisted = wishlistedProducts.contains(product.id),
                onWishlistProduct = { onWishlistProduct(product.id) },
                onProductClicked = { onProductClicked(product.id) },
            )
        }
    }
}

@Composable
private fun SearchResultShimmer(
    modifier: Modifier = Modifier,
    searchQuery: String = "",
) {
    val state = rememberLazyGridState()
    LazyVerticalGrid(
        state = state,
        columns = GridCells.Adaptive(160.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        item(
            span = {
                GridItemSpan(this.maxLineSpan)
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Result for \"$searchQuery\"",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(80.dp, 24.dp)
                        .shimmerEffect()
                )
            }
        }
        items(6) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                ProductItemShimmer()
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SearchResultListPreview() {
    val products = listOf(
        Product(id = "1", name = "Product 1", rating = 4.8f, basePrice = 155.3f),
        Product(id = "2", name = "Product 2", rating = 4.5f, basePrice = 237f),
        Product(id = "3", name = "Product 3", rating = 4.6f, basePrice = 99f),
        Product(id = "4", name = "Product 4", rating = 3.8f, basePrice = 170.9f),
        Product(id = "5", name = "Product 5", rating = 3.4f, basePrice = 360.3f),
    )
    KhanaTheme {
        SearchResultList(
            modifier = Modifier.padding(horizontal = 24.dp),
            products = emptyList(),
            onProductClicked = {},
            onWishlistProduct = {},
            wishlistedProducts = listOf("2", "5"),
            isLoading = false,
        )
    }
}
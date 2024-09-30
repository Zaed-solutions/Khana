package org.zaed.khana.presentation.search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.Product
import org.zaed.khana.presentation.home.components.ProductItem
import org.zaed.khana.presentation.theme.KhanaTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResultList(
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    products: List<Product>,
    onProductClicked: (String) -> Unit,
    onWishlistProduct: (String) -> Unit,
    wishlistedProducts: List<String>,
    screenWidth: Int,
) {
    val state = rememberLazyGridState()
    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(text = "${products.size} found", style = MaterialTheme.typography.titleLarge)
            }
        }
        items(products.size) { index ->
            val product = products[index]
            ProductItem(
                productName = product.name,
                productThumbnailImageLink = product.thumbnailImageLink,
                productRating = product.rating,
                productPrice = product.basePrice,
                isWishlisted = wishlistedProducts.contains(product.id),
                onWishlistProduct = { onWishlistProduct(product.id) },
                onProductClicked = { onProductClicked(product.id) },
                screenWidth = screenWidth
            )
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
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    KhanaTheme {
        SearchResultList(
            products = products,
            onProductClicked = {},
            onWishlistProduct = {},
            wishlistedProducts = listOf("2", "5"),
            screenWidth = screenWidth.value.toInt()
        )
    }
}
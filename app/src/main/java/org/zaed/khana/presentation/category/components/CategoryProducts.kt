package org.zaed.khana.presentation.category.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.Product
import org.zaed.khana.presentation.home.components.ProductItem
import org.zaed.khana.presentation.home.components.ProductItemShimmer

@Composable
fun CategoryProducts(
    modifier: Modifier = Modifier,
    products: List<Product>,
    isLoading: Boolean,
    wishlistedProductsIds: List<String>,
    onWishlistProduct: (String) -> Unit,
    onProductClicked: (String) -> Unit
) {
    Crossfade(targetState = isLoading) { state ->
        when{
            state -> {CategoryProductsShimmer(modifier)}
            else -> {
                CategoryProductsContent(modifier, products, wishlistedProductsIds, onWishlistProduct, onProductClicked)
            }
        }
    }
}

@Composable
private fun CategoryProductsContent(
    modifier: Modifier,
    products: List<Product>,
    wishlistedProductsIds: List<String>,
    onWishlistProduct: (String) -> Unit,
    onProductClicked: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products.size) { index ->
            val product = products[index]
            ProductItem(
                modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center).animateItem(),
                productName = product.name,
                productThumbnailImageLink = product.thumbnailImageLink,
                productRating = product.rating,
                productPrice = product.basePrice,
                isWishlisted = wishlistedProductsIds.contains(product.id),
                onWishlistProduct = { onWishlistProduct(product.id) },
                onProductClicked = { onProductClicked(product.id) },
            )
        }
    }
}

@Composable
fun CategoryProductsShimmer(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
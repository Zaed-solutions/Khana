package org.zaed.khana.presentation.wishlist.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.Product
import org.zaed.khana.presentation.components.EmptyListScreen
import org.zaed.khana.presentation.home.components.ProductItem
import org.zaed.khana.presentation.util.shimmerEffect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WishlistedProductsList(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    products: List<Product>,
    onWishlistProduct: (String) -> Unit,
    onProductClicked: (String) -> Unit
) {
    val isEmpty = products.isEmpty()
    Crossfade(targetState = isEmpty, label = "Wishlisted Products") { state ->
        when {
            isLoading -> {
                WishlistedProductsShimmer(modifier = modifier)
            }
            state -> {
                EmptyListScreen(modifier = modifier)
            }
            else -> {
                LazyVerticalGrid(
                    modifier = modifier,
                    columns = GridCells.Adaptive(160.dp),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(products.size) { index ->
                        val product = products[index]
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            ProductItem(
                                modifier = Modifier.animateItem(),
                                productName = product.name,
                                productThumbnailImageLink = product.thumbnailImageLink,
                                productRating = product.rating,
                                productPrice = product.basePrice,
                                isWishlisted = true,
                                onWishlistProduct = {
                                    onWishlistProduct(product.id)
                                },
                                onProductClicked = { onProductClicked(product.id) }
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun WishlistedProductsShimmer(
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(6) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(MaterialTheme.shapes.large)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .size(width = 160.dp, height = 24.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .size(width = 110.dp, height = 24.dp)
                        .shimmerEffect()
                )
            }
        }
    }
}
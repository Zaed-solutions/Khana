package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.Product
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun ProductItems(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    products: List<Product>,
    wishlistedProducts: List<String>,
    onProductClicked: (String) -> Unit,
    onWishlistProduct: (String) -> Unit,
) {
    if (isLoading) {
        ProductItemsShimmer()
    } else {
        LazyRow(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products.size) { index ->
                val product = products[index]
                val isWishlisted = wishlistedProducts.contains(product.id)
                ProductItem(
                    productName = product.name,
                    productThumbnailImageLink = product.thumbnailImageLink,
                    productRating = product.rating,
                    productPrice = product.basePrice,
                    isWishlisted = isWishlisted,
                    onWishlistProduct = { onWishlistProduct(product.id) },
                    onProductClicked = { onProductClicked(product.id) },
                )
            }
        }
    }
}

@Composable
fun ProductItemsShimmer(modifier: Modifier = Modifier) {
    LazyRow (
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(3){
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ){
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(MaterialTheme.shapes.large)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(160.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(100.dp)
                        .shimmerEffect()
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProductItemsPreview() {
    val products = listOf(
        Product(id = "1", name = "Product 1", rating = 4.8f, basePrice = 155.3f),
        Product(id = "2", name = "Product 2", rating = 4.5f, basePrice = 237f),
        Product(id = "3", name = "Product 3", rating = 4.6f, basePrice = 99f),
        Product(id = "4", name = "Product 4", rating = 3.8f, basePrice = 170.9f),
        Product(id = "5", name = "Product 5", rating = 3.4f, basePrice = 360.3f),
        Product(id = "6", name = "Product 6", rating = 4.0f, basePrice = 10f),
        Product(id = "3", name = "Product 3", rating = 4.6f, basePrice = 99f),
        Product(id = "4", name = "Product 4", rating = 3.8f, basePrice = 170.9f),
        Product(id = "5", name = "Product 5", rating = 3.4f, basePrice = 360.3f),
        Product(id = "6", name = "Product 6", rating = 4.0f, basePrice = 10f),
    )
    val wishlistedProducts = listOf("2", "4")
    KhanaTheme {
        ProductItems(
            isLoading = true,
            products = products,
            wishlistedProducts = wishlistedProducts,
            onProductClicked = {},
            onWishlistProduct = {},
        )
    }
}
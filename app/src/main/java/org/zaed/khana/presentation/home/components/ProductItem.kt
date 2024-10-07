package org.zaed.khana.presentation.home.components

import android.icu.text.DecimalFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ProductItem(
    productName: String,
    productThumbnailImageLink: String,
    productRating: Float,
    productPrice: Float,
    isWishlisted: Boolean,
    onWishlistProduct: () -> Unit,
    onProductClicked: () -> Unit,
    screenWidth: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .width(((screenWidth - 40) / 2).dp)
            .clickable { onProductClicked() }
    ) {
        //image and wishlist
        ThumbnailSection(
            productThumbnailImageLink = productThumbnailImageLink,
            screenWidth = screenWidth,
            onWishlistProduct = onWishlistProduct,
            isWishlisted = isWishlisted
        )
        //title and rating
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = productName, modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    tint = Color.Yellow,
                    contentDescription = null
                )
                Text(text = DecimalFormat("#.#").format(productRating))
            }
        }
        //price
        Text(text = "\$${DecimalFormat("#.00").format(productPrice)}")
    }
}

@Composable
private fun ThumbnailSection(
    productThumbnailImageLink: String,
    screenWidth: Int,
    onWishlistProduct: () -> Unit,
    isWishlisted: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clip(MaterialTheme.shapes.large)
    ) {
        SubcomposeAsyncImage(
            model = productThumbnailImageLink,
            contentDescription = "product thumbnail",
            modifier = Modifier
                .align(Alignment.Center)
                .size(((screenWidth - 40) / 2).dp),
            contentScale = ContentScale.FillBounds
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator(Modifier.size(36.dp))
            } else {
                SubcomposeAsyncImageContent()
            }
        }
        IconButton(
            onClick = { onWishlistProduct() },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                    alpha = 0.5f
                ), contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp, top = 8.dp)
        ) {
            Icon(
                imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true, name = "wishlisted product")
@Composable
private fun ThumbnailSectionPreview2() {
    KhanaTheme {
        ThumbnailSection(
            productThumbnailImageLink = "https://www.imge.com/test.jpg",
            screenWidth = 400,
            onWishlistProduct = { /*TODO*/ },
            isWishlisted = false
        )
    }
}

@Preview(showBackground = true, name = "not wishlisted product")
@Composable
private fun ThumbnailSectionPreview1() {
    KhanaTheme {
        ThumbnailSection(
            productThumbnailImageLink = "https://www.imge.com/test.jpg",
            screenWidth = 400,
            onWishlistProduct = { /*TODO*/ },
            isWishlisted = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductItemPreview() {
    KhanaTheme {
        ProductItem(
            productName = "Test Title",
            productThumbnailImageLink = "https://www.imge.com/test.jpg",
            productRating = 4.7f,
            productPrice = 183f,
            isWishlisted = false,
            onWishlistProduct = { /*TODO*/ },
            onProductClicked = { /*TODO*/ },
            screenWidth = 400
        )
    }
}
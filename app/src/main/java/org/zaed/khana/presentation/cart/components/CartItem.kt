package org.zaed.khana.presentation.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Color
import org.zaed.khana.presentation.components.StatefulAsyncImage
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect
import org.zaed.khana.presentation.util.toMoney

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    item: CartItem,
    onIncrementQuantity: () -> Unit = {},
    onDecrementQuantity: () -> Unit = {},
    showControlQuantitySection: Boolean = true
) {
    val price = item.productBasePrice * item.quantity
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatefulAsyncImage(
            modifier = Modifier.size(100.dp),
            imageUrl = item.productThumbnail,
            shadowElevation = 5.dp,
            shape = MaterialTheme.shapes.medium
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 100.dp)
                .padding(start = 8.dp)
        ) {
            Text(
                text = "${item.productColor.name} ${item.productName}",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "Size: ${item.productSize}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = price.toMoney(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                if (showControlQuantitySection) {
                    Column {
                        ItemQuantityController(
                            quantity = item.quantity,
                            onIncrementQuantity = onIncrementQuantity,
                            onDecrementQuantity = onDecrementQuantity
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun CartItemShimmer(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 100.dp)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(20.dp)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(20.dp)
                    .shimmerEffect()
            )

        }

    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CartItemPreview() {
    KhanaTheme {
        CartItem(
            modifier = Modifier.padding(vertical = 16.dp),
            item = CartItem(
                quantity = 1,
                productName = "Jacket",
                productColor = Color(name = "Brown"),
                productSize = "XL",
                productBasePrice = 83.97f
            ),
            onIncrementQuantity = { /*TODO*/ },
            onDecrementQuantity = { /*TODO*/ })
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CartItemShimmerPreview() {
    KhanaTheme {
        CartItemShimmer(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
            )
    }
}
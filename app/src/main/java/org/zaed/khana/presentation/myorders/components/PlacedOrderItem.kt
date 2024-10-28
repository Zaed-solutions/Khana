package org.zaed.khana.presentation.myorders.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.components.StatefulAsyncImage
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect
import org.zaed.khana.presentation.util.toMoney

@Composable
fun PlacedOrderItem(
    modifier: Modifier = Modifier,
    thumbnailUrl: String,
    title: String,
    quantity: Int,
    size: String,
    price: Float,
    showButton: Boolean = true,
    buttonText: String = "",
    onButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatefulAsyncImage(
                modifier = Modifier.size(80.dp),
                imageUrl = thumbnailUrl,
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
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.size_qty, size, quantity),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = price.toMoney(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    if (showButton ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = { onButtonClicked() },
                            modifier = Modifier.height(32.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            Text(text = buttonText)
                        }
                    }
                }
            }
        }
        HorizontalDivider(thickness = 0.5.dp)
    }
}

@Composable
fun PlacedOrderItemShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 80.dp)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
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
                        .height(16.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                        .shimmerEffect()
                )
            }
        }
        HorizontalDivider(thickness = 0.5.dp)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PlacedOrderItemPreview() {
    KhanaTheme {
        Column{
            PlacedOrderItem(
                thumbnailUrl = "",
                title = "Product Title",
                quantity = 10,
                size = "M",
                price = 251f,
                buttonText = "Track Order"
            ) {

            }
            PlacedOrderItemShimmer()
        }
    }
}
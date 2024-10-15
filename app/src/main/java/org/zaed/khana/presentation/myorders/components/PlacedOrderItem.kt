package org.zaed.khana.presentation.myorders.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.toMoney

@Composable
fun PlacedOrderItem(
    modifier: Modifier = Modifier,
    thumbnailUrl: String,
    title: String,
    quantity: Int,
    size: String,
    price: Float,
    buttonText: String,
    onButtonClicked: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            modifier = Modifier.size(80.dp),
            shadowElevation = 5.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = null,
            )
        }
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PlacedOrderItemPreview() {
    KhanaTheme {
        PlacedOrderItem(
            thumbnailUrl = "",
            title = "Product Title",
            quantity = 10,
            size = "M",
            price = 251f,
            buttonText = "Track Order"
        ) {

        }
    }
}
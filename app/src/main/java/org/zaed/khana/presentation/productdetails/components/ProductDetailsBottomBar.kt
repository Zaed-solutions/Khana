package org.zaed.khana.presentation.productdetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.toMoney

@Composable
fun ProductDetailsBottomBar(
    price: Float,
    isAddedToCart: Boolean,
    onAddToCartClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column{
                Text(text = stringResource(R.string.total_price))
                Text(text = price.toMoney())
            }
            Button(
                enabled = !isAddedToCart,
                onClick = { onAddToCartClicked() }) {
                Icon(
                    imageVector = if(isAddedToCart) Icons.Default.Check else Icons.Default.ShoppingBag,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 16.dp, end = 4.dp)
                )
                Text(
                    text = if(isAddedToCart) stringResource(R.string.added_to_cart) else stringResource(R.string.add_to_cart),
                    modifier = Modifier.padding(start = 4.dp, end = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailsBottomBarPreview() {
    KhanaTheme {
        ProductDetailsBottomBar(price = 183.33f, true) {

        }
    }
}
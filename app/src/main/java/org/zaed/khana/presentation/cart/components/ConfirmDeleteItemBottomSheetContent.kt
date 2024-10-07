package org.zaed.khana.presentation.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Color
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ConfirmDeleteItemBottomSheetContent(
    modifier: Modifier = Modifier,
    item: CartItem,
    onRemoveCartItem: (String) -> Unit,
    onCancel: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.remove_from_cart),
            style = MaterialTheme.typography.headlineMedium
        )
        CartItem(
            item = item,
            showControlQuantitySection = false,
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilledTonalButton(onClick = { onCancel() }) {
                Text(stringResource(R.string.cancel))
            }
            Button(onClick = { onRemoveCartItem(item.productId) }) {
                Text(text = stringResource(R.string.yes_remove))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ConfirmDeleteItemBottomSheetContentPreview() {
    KhanaTheme {
        ConfirmDeleteItemBottomSheetContent(
            item = CartItem(
                quantity = 1,
                productName = "Jacket",
                productColor = Color(name = "Brown"),
                productSize = "XL",
                productBasePrice = 83.97f
            ), onRemoveCartItem = {}) {

        }
    }
}
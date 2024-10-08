package org.zaed.khana.presentation.checkout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.presentation.cart.components.CartItem

@Composable
fun OrderListSection(
    modifier: Modifier = Modifier,
    cartItems: List<CartItem>) {
    CheckoutScreenSection(title = "Order List") {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cartItems.size){ index ->
                val item = cartItems[index]
                CartItem(
                    item = item,
                    showControlQuantitySection = false
                )
            }
        }
    }
}
package org.zaed.khana.presentation.cart.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.presentation.components.EmptyListScreen

@Composable
fun CartItemsList(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    cartItems: List<CartItem>,
    onDeleteCartItem: (String) -> Unit,
    onIncrementItemQuantity: (String) -> Unit,
    onDecrementItemQuantity: (String) -> Unit,
) {
    val isEmpty = cartItems.isEmpty()
    Crossfade(targetState = isEmpty, label = "cart items") { state ->
        when {
            isLoading -> CartItemsShimmer(modifier = modifier)
            state -> EmptyListScreen(modifier = modifier)
            else -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(cartItems.size) { index ->
                        val item = cartItems[index]
                        SwipeToDeleteContainer(
                            onDelete = {
                                onDeleteCartItem(item.id)
                            }
                        ) {
                            CartItem(
                                item = item,
                                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                                onIncrementQuantity = { onIncrementItemQuantity(item.id) },
                                onDecrementQuantity = {
                                    onDecrementItemQuantity(item.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CartItemsShimmer(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(6) {
            CartItemShimmer()
        }
    }
}
package org.zaed.khana.presentation.myorders.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.OrderedCartItem
import org.zaed.khana.presentation.components.EmptyListScreen
import org.zaed.khana.presentation.myorders.OrdersTabs

@Composable
fun PlacedOrdersList(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    selectedTab: OrdersTabs,
    items: List<OrderedCartItem>,
    onTrackOrderClicked: (String, String) -> Unit,
    onLeaveReviewClicked: (String, String) -> Unit,
    onReorderClicked: (String) -> Unit
) {
    Crossfade(targetState = isLoading to items, label = "placed orders list") { state ->
        when{
            state.first -> {
                PlacedOrdersShimmer(modifier)
            }
            state.second.isEmpty() -> {
                EmptyListScreen(modifier)
            }
            else -> {
                PlacedOrdersContent(
                    modifier = modifier,
                    items = items,
                    selectedTab = selectedTab,
                    onTrackOrderClicked = onTrackOrderClicked,
                    onLeaveReviewClicked = onLeaveReviewClicked,
                    onReorderClicked = onReorderClicked
                )
            }
        }
    }
}

@Composable
private fun PlacedOrdersContent(
    modifier: Modifier,
    items: List<OrderedCartItem>,
    selectedTab: OrdersTabs,
    onTrackOrderClicked: (String, String) -> Unit,
    onLeaveReviewClicked: (String, String) -> Unit,
    onReorderClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(items.size) { index ->
            val item = items[index]
            PlacedOrderItem(
                modifier = Modifier.animateItem(),
                thumbnailUrl = item.data.productThumbnail,
                title = item.data.productColor.name + " " + item.data.productName,
                quantity = item.data.quantity,
                size = item.data.productSize,
                price = item.data.productBasePrice,
                buttonText = selectedTab.buttonText,
            ) {
                when (selectedTab) {
                    OrdersTabs.ACTIVE -> {
                        onTrackOrderClicked(item.orderId, item.data.id)
                    }

                    OrdersTabs.COMPLETED -> {
                        onLeaveReviewClicked(item.orderId, item.data.id)
                    }

                    OrdersTabs.CANCELLED -> {
                        onReorderClicked(item.orderId)
                    }
                }
            }
        }
    }
}

@Composable
fun PlacedOrdersShimmer(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(6) { _ ->
            PlacedOrderItemShimmer()
        }
    }
}
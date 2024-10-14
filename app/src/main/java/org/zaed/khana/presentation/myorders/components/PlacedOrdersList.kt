package org.zaed.khana.presentation.myorders.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.presentation.myorders.OrdersTabs

@Composable
fun PlacedOrdersList(
    modifier: Modifier = Modifier,
    selectedTab: OrdersTabs,
    items: List<CartItem>,
    onTrackOrderClicked: (String) -> Unit,
    onLeaveReviewClicked: (String) -> Unit,
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
                thumbnailUrl = item.productThumbnail,
                title = item.productColor.name + " " + item.productName,
                quantity = item.quantity,
                size = item.productSize,
                price = item.productBasePrice,
                buttonText = selectedTab.buttonText,
            ) {
                when (selectedTab) {
                    OrdersTabs.ACTIVE -> {
                        onTrackOrderClicked(item.id)
                    }

                    OrdersTabs.COMPLETED -> {
                        onLeaveReviewClicked(item.id)
                    }

                    OrdersTabs.CANCELLED -> {
                        onReorderClicked(item.id)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(thickness = 0.5.dp)
        }

    }
}
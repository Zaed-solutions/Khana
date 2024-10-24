package org.zaed.khana.presentation.trackorder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.Clock
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.model.Order
import org.zaed.khana.data.model.OrderStatus
import org.zaed.khana.presentation.myorders.components.PlacedOrderItem
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.trackorder.components.OrderDetailsSection
import org.zaed.khana.presentation.trackorder.components.OrderStatusSection

@Composable
fun TrackOrderScreen(
    modifier: Modifier = Modifier,
    viewModel: TrackOrderViewModel = koinViewModel(),
    orderId: String,
    cartItemId: String,
    onBackPressed: () -> Unit,
) {
    LaunchedEffect(key1 = true) {
        viewModel.init(orderId, cartItemId)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    TrackOrderScreenContent(
        modifier = modifier,
        item = state.cartItem,
        order = state.order,
        onAction = { action ->
            when(action){
                TrackOrderUiAction.OnBackPressed -> onBackPressed()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrackOrderScreenContent(
    modifier: Modifier = Modifier,
    item: CartItem,
    order: Order,
    onAction: (TrackOrderUiAction) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.track_order),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    OutlinedIconButton(onClick = { onAction(TrackOrderUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PlacedOrderItem(
                modifier = Modifier.padding(top = 16.dp),
                thumbnailUrl = item.productThumbnail,
                title = item.productColor.name + " " + item.productName,
                quantity = item.quantity,
                size = item.productSize,
                price = item.productBasePrice,
                showButton = false,
            )
            HorizontalDivider(thickness = 0.5.dp)
            OrderDetailsSection(
                expectedDeliveryEpochSeconds = order.expectedDeliveryEpochSeconds,
                trackingId = order.trackingId
            )
            HorizontalDivider(thickness = 0.5.dp)
            OrderStatusSection(
                orderStatus = OrderStatus.valueOf(order.orderStatus),
                confirmedEpochSeconds = order.confirmedEpochSeconds,
                shippedEpochSeconds = order.shippedEpochSeconds,
                deliveredEpochSeconds = order.deliveredEpochSeconds
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TrackOrderScreenContentPreview() {
    val nowEpochSeconds = Clock.System.now().epochSeconds
    val order = Order(
        expectedDeliveryEpochSeconds = nowEpochSeconds + 15000,
        trackingId = "TRCK1234567890",
        confirmedEpochSeconds = nowEpochSeconds - 5000,
        shippedEpochSeconds = nowEpochSeconds + 13000,
        deliveredEpochSeconds = nowEpochSeconds + 15000,
        orderStatus = OrderStatus.CONFIRMED.name
    )
    val item = CartItem(
        productThumbnail = "",
        productName = "Shirt",
        productColor = Color(name = "White"),
        productSize = "XL",
        productBasePrice = 100f,
        quantity = 1
    )
    KhanaTheme {
        TrackOrderScreenContent(onAction = {}, item = item, order = order)
    }
}
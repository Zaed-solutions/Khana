package org.zaed.khana.presentation.trackorder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Order
import org.zaed.khana.presentation.myorders.components.PlacedOrderItem
import org.zaed.khana.presentation.trackorder.components.OrderDetailsSection
import org.zaed.khana.presentation.trackorder.components.OrderStatusSection

@Composable
fun TrackOrderScreen(
    modifier: Modifier = Modifier,
    viewModel: TrackOrderViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    orderId: String,
    cartItemId: String,
) {
    LaunchedEffect(key1 = true) {
        viewModel.init(orderId, cartItemId)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    TrackOrderScreenContent(
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
    onAction: (TrackOrderUiAction) -> Unit,
    item: CartItem,
    order: Order,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.track_order)
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
            OrderDetailsSection(
                expectedDeliveryEpochSeconds = order.expectedDeliveryEpochSeconds,
                trackingId = order.trackingId
            )
            OrderStatusSection()
        }
    }
}
package org.zaed.khana.presentation.myorders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.model.OrderedCartItem
import org.zaed.khana.presentation.myorders.components.PlacedOrdersList
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun MyOrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: MyOrdersViewModel = koinViewModel(),
    onNavigateToLeaveReview:(String, String) -> Unit,
    onNavigateToTrackOrder: (String, String) -> Unit,
    onBackPressed: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    MyOrdersScreenContent(
        modifier = modifier,
        onAction = { action ->
            when (action) {
                is MyOrdersUiAction.OnBackPressed -> onBackPressed()
                is MyOrdersUiAction.OnTrackItemClicked -> {
                   onNavigateToTrackOrder(action.orderId, action.itemId)
                }

                is MyOrdersUiAction.OnLeaveItemReviewClicked -> {
                    onNavigateToLeaveReview(action.orderId, action.itemId)
                }

                else -> viewModel.handleUiAction(action)
            }
        },
        items = state.displayedItems
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyOrdersScreenContent(
    modifier: Modifier = Modifier,
    onAction: (MyOrdersUiAction) -> Unit,
    items: List<OrderedCartItem>,
) {
    var selectedTab: OrdersTabs by remember { mutableStateOf(OrdersTabs.ACTIVE) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.my_orders),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    OutlinedIconButton(onClick = { onAction(MyOrdersUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            PrimaryTabRow(selectedTabIndex = selectedTab.ordinal) {
                OrdersTabs.entries.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTab.ordinal == index,
                        onClick = {
                            selectedTab = tab
                            onAction(MyOrdersUiAction.OnChangeTab(tab))
                        },
                        text = {
                            Text(
                                text = tab.displayName,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
            val pagerState = rememberPagerState(pageCount = { OrdersTabs.entries.size })
            HorizontalPager(state = pagerState) { pageIndex ->
                PlacedOrdersList(
                    selectedTab = selectedTab,
                    items = items,
                    onTrackOrderClicked = { orderId, itemId ->
                        onAction(
                            MyOrdersUiAction.OnTrackItemClicked(
                                orderId,
                                itemId
                            )
                        )
                    },
                    onLeaveReviewClicked = { orderId, itemId ->
                        onAction(
                            MyOrdersUiAction.OnLeaveItemReviewClicked(
                                orderId,
                                itemId
                            )
                        )
                    },
                    onReorderClicked = { itemId ->
                        onAction(
                            MyOrdersUiAction.OnReorderItemClicked(
                                itemId
                            )
                        )
                    }
                )
            }
        }
    }
}

enum class OrdersTabs(val displayName: String, val buttonText: String) {
    ACTIVE("Active", "Track Order"),
    COMPLETED("Completed", "Leave Review"),
    CANCELLED("Cancelled", "Re-order")
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun MyOrdersScreenContentPreview() {
    val items = listOf(
        CartItem(
            id = "1",
            userId = "1",
            productId = "1",
            productThumbnail = "https://picsum.photos/200/300",
            productName = "Jacket",
            productColor = Color(name = "Purple", hex = "quam"),
            productSize = "XL",
            productBasePrice = 189f,
            appliedDiscountPercentage = 2.3f,
            quantity = 2
        ),
        CartItem(
            id = "2",
            userId = "1",
            productId = "2",
            productThumbnail = "https://picsum.photos/200/300",
            productName = "Pants",
            productColor = Color(name = "Blue", hex = "quam"),
            productSize = "M",
            productBasePrice = 50f,
            appliedDiscountPercentage = 2.3f,
            quantity = 3
        ),
        CartItem(
            id = "3",
            userId = "1",
            productId = "3",
            productThumbnail = "https://picsum.photos/200/300",
            productName = "Shirt",
            productColor = Color(name = "White", hex = "quam"),
            productSize = "XL",
            productBasePrice = 100f,
            appliedDiscountPercentage = 2.3f,
            quantity = 1
        ),
    )
    KhanaTheme {
        MyOrdersScreenContent(
            onAction = {},
            items = listOf(items.map { OrderedCartItem(orderId = "", data = it) }).flatten()
        )
    }
}
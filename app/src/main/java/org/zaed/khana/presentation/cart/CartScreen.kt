package org.zaed.khana.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Color
import org.zaed.khana.presentation.cart.components.CartItem
import org.zaed.khana.presentation.cart.components.ConfirmDeleteItemBottomSheetContent
import org.zaed.khana.presentation.cart.components.ProceedToCheckoutBottomSheetContent
import org.zaed.khana.presentation.cart.components.SwipeToDeleteContainer
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    viewModel: CartViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    CartScreenContent(
        cartItems = state.cartItems,
        onAction = { action ->
            when (action) {
                CartUiAction.OnBackPressed -> onBackPressed()
                CartUiAction.OnProceedToCheckout -> TODO("navigate to checkout")
                else -> viewModel.handleUiAction(action)
            }
        },
        discountPercentage = state.discountPercentage,
        subTotalPrice = state.subTotalPrice,
        deliveryFee = state.deliveryFee
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartScreenContent(
    modifier: Modifier = Modifier,
    cartItems: List<CartItem>,
    onAction: (CartUiAction) -> Unit,
    discountPercentage: Float,
    subTotalPrice: Float,
    deliveryFee: Float,
) {
    var swipedItemIndex by remember { mutableIntStateOf(-1) }
    var isItemSwiped by remember { mutableStateOf(false) }
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.my_cart)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(CartUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        isItemSwiped = false
                        showBottomSheet = true
                    }) {
                        Text(text = "Checkout")
                    }
                })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cartItems.size) { index ->
                val item = cartItems[index]
                SwipeToDeleteContainer(
                    onDelete = {
                        swipedItemIndex = index
                        isItemSwiped = true
                        showBottomSheet = true
                    }
                ) {
                    CartItem(
                        item = item,
                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                        onIncrementQuantity = { onAction(CartUiAction.OnIncrementItemQuantity(item.productId)) },
                        onDecrementQuantity = {
                            if (item.quantity == 1) {
                                swipedItemIndex = index
                                isItemSwiped = true
                                showBottomSheet = true
                            } else {
                                onAction(CartUiAction.OnDecrementItemQuantity(item.productId))
                            }

                        }
                    )
                }
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                if (isItemSwiped) {
                    val item = cartItems[swipedItemIndex]
                    ConfirmDeleteItemBottomSheetContent(
                        item = item,
                        onRemoveCartItem = {
                            onAction(CartUiAction.OnRemoveItemFromCart(item.productId))
                            showBottomSheet = false
                        },
                        onCancel = {
                            showBottomSheet = false
                        }
                    )
                } else {
                    ProceedToCheckoutBottomSheetContent(
                        discountPercentage = discountPercentage,
                        subTotalPrice = subTotalPrice,
                        deliveryFee = deliveryFee,
                        onApplyPromoCode = { code ->
                            onAction(CartUiAction.OnApplyPromoCode(code))
                        },
                        onProceedToCheckout = {
                            onAction(CartUiAction.OnProceedToCheckout)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CartScreenContentPreview() {
    val items = listOf(
        CartItem(
            quantity = 1,
            productName = "Jacket",
            productColor = Color(name = "Brown"),
            productSize = "XL",
            productBasePrice = 999.99f
        ),
        CartItem(
            quantity = 1,
            productName = "Shirt",
            productColor = Color(name = "White"),
            productSize = "XL",
            productBasePrice = 199.99f
        ),
        CartItem(
            quantity = 1,
            productName = "Pants",
            productColor = Color(name = "Blue"),
            productSize = "35",
            productBasePrice = 249.99f
        ),
        CartItem(
            quantity = 1,
            productName = "T-Shirt",
            productColor = Color(name = "Pink"),
            productSize = "XL",
            productBasePrice = 159.99f
        ),
        CartItem(
            quantity = 10,
            productName = "Shoes",
            productColor = Color(name = "White"),
            productSize = "45",
            productBasePrice = 320f
        )
    )
    KhanaTheme {
        CartScreenContent(
            cartItems = items,
                onAction ={},
                discountPercentage = 0.05f,
                subTotalPrice = 2199.95f,
                deliveryFee = 25f
            )
    }
}
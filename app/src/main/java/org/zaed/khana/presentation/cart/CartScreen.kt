package org.zaed.khana.presentation.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Color
import org.zaed.khana.presentation.cart.components.CartItemsList
import org.zaed.khana.presentation.cart.components.ConfirmDeleteItemBottomSheetContent
import org.zaed.khana.presentation.cart.components.ProceedToCheckoutBottomSheetContent
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onNavigateToCheckout: () -> Unit,
    onNavigateToCoupons: () -> Unit,
    viewModel: CartViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    CartScreenContent(
        modifier = modifier,
        isLoading = state.isLoading,
        cartItems = state.cartItems,
        onAction = { action ->
            when (action) {
                CartUiAction.OnBackPressed -> onBackPressed()
                CartUiAction.OnProceedToCheckout -> onNavigateToCheckout()
                CartUiAction.OnViewCoupons -> onNavigateToCoupons()
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
    isLoading: Boolean,
    cartItems: List<CartItem>,
    onAction: (CartUiAction) -> Unit,
    discountPercentage: Float,
    subTotalPrice: Float,
    deliveryFee: Float,
) {
    var swipedItemId by remember { mutableStateOf("") }
    var isItemSwiped by remember { mutableStateOf(false) }
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    val bottomSheetModifier = Modifier
        .windowInsetsPadding(WindowInsets.ime)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.my_cart),
                        style = MaterialTheme.typography.titleLarge
                    )
                        },
                navigationIcon = {
                    OutlinedIconButton(onClick = { onAction(CartUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    TextButton(
                        enabled = cartItems.isNotEmpty(),
                        onClick = {
                            isItemSwiped = false
                            showBottomSheet = true
                        }) {
                        Text(text = "Checkout")
                    }
                })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CartItemsList(
                isLoading = isLoading,
                cartItems = cartItems,
                onDeleteCartItem = { id ->
                    swipedItemId = id
                    isItemSwiped = true
                    showBottomSheet = true
                },
                onIncrementItemQuantity = { id ->
                    onAction(CartUiAction.OnIncrementItemQuantity(id))
                },
                onDecrementItemQuantity = { id ->
                    onAction(CartUiAction.OnDecrementItemQuantity(id))
                }
            )
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    windowInsets = WindowInsets.ime.only(WindowInsetsSides.Bottom),
                    sheetState = sheetState
                ) {
                    if (isItemSwiped) {
                        val item = cartItems.first { it.id == swipedItemId }
                        ConfirmDeleteItemBottomSheetContent(
                            modifier = bottomSheetModifier,
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
                            modifier = bottomSheetModifier,
                            discountPercentage = discountPercentage,
                            subTotalPrice = subTotalPrice,
                            deliveryFee = deliveryFee,
                            onApplyPromoCode = { code ->
                                onAction(CartUiAction.OnApplyPromoCode(code))
                            },
                            onViewCouponsClicked = {
                                onAction(CartUiAction.OnViewCoupons)
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
            onAction = {},
            discountPercentage = 0.05f,
            subTotalPrice = 2199.95f,
            deliveryFee = 25f,
            isLoading = false
        )
    }
}
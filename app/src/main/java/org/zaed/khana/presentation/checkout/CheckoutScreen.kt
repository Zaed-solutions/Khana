package org.zaed.khana.presentation.checkout

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.model.ShippingType
import org.zaed.khana.presentation.checkout.components.AddAddressBottomSheet
import org.zaed.khana.presentation.checkout.components.CheckoutBottomBar
import org.zaed.khana.presentation.checkout.components.OrderListSection
import org.zaed.khana.presentation.checkout.components.ShippingAddressBottomSheet
import org.zaed.khana.presentation.checkout.components.ShippingAddressSection
import org.zaed.khana.presentation.checkout.components.ShippingTypeBottomSheet
import org.zaed.khana.presentation.checkout.components.ShippingTypeSection
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    onNavigateToPaymentScreen: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = state.isOrderPlaced) {
        if (state.isOrderPlaced) {
            onNavigateToPaymentScreen(state.orderId)
        }
    }
    CheckoutScreenContent(
        modifier = modifier,
        shippingAddress = state.selectedShippingAddress,
        shippingType = state.selectedShippingType,
        addresses = state.shippingAddresses,
        cartItems = state.cartItems,
        isLoading = state.isLoading,
        onAction = { action ->
            when (action) {
                CheckoutUiAction.OnBackPressed -> onBackPressed()
                else -> viewModel.handleUiAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CheckoutScreenContent(
    modifier: Modifier = Modifier,
    shippingAddress: ShippingAddress,
    addresses: List<ShippingAddress>,
    shippingType: ShippingType,
    cartItems: List<CartItem>,
    isLoading: Boolean,
    onAction: (CheckoutUiAction) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var shownBottomSheet by remember { mutableStateOf(ShownBottomSheet.NONE) }
    val bottomSheetModifier = Modifier
        .windowInsetsPadding(WindowInsets.ime)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.checkout),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    OutlinedIconButton(onClick = { onAction(CheckoutUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            CheckoutBottomBar(
                onContinueToPayment = { onAction(CheckoutUiAction.OnContinueToPaymentClicked) },
                enabled = !isLoading && shippingAddress.id.isNotBlank()
            )
        }
    ) { paddingValues ->
        Column(
            modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ShippingAddressSection(
                isLoading = isLoading,
                shippingAddress = shippingAddress,
                onChangeAddressClicked = { shownBottomSheet = ShownBottomSheet.SHIPPING_ADDRESS },
                onAddShippingAddressClicked = { shownBottomSheet = ShownBottomSheet.ADD_ADDRESS }
            )
            ShippingTypeSection(
                isLoading = isLoading,
                shippingType = shippingType,
                onChangeTypeClicked = { shownBottomSheet = ShownBottomSheet.SHIPPING_TYPE }
            )
            OrderListSection(
                isLoading = isLoading,
                cartItems = cartItems
            )
        }
        if (shownBottomSheet != ShownBottomSheet.NONE) {
            ModalBottomSheet(
                onDismissRequest = { shownBottomSheet = ShownBottomSheet.NONE },
                windowInsets = WindowInsets.ime.only(WindowInsetsSides.Bottom),
                sheetState = sheetState
            ) {
                AnimatedContent(targetState = shownBottomSheet, label = "") { state ->
                    when (state) {
                        ShownBottomSheet.SHIPPING_ADDRESS -> {
                            ShippingAddressBottomSheet(
                                modifier = bottomSheetModifier,
                                selectedAddress = shippingAddress,
                                addresses = addresses,
                                onChangeShippingAddress = { address ->
                                    onAction(CheckoutUiAction.OnChangeShippingAddress(address))
                                    shownBottomSheet = ShownBottomSheet.NONE
                                },
                                onAddNewAddressClicked = {
                                    shownBottomSheet = ShownBottomSheet.ADD_ADDRESS
                                }
                            )
                        }

                        ShownBottomSheet.SHIPPING_TYPE -> {
                            ShippingTypeBottomSheet(
                                modifier = bottomSheetModifier,
                                selectedType = shippingType,
                                onChangeShippingType = { type ->
                                    onAction(CheckoutUiAction.OnChangeShippingType(type))
                                    shownBottomSheet = ShownBottomSheet.NONE
                                }
                            )
                        }

                        ShownBottomSheet.ADD_ADDRESS -> {
                            AddAddressBottomSheet(
                                modifier = bottomSheetModifier,
                                onAddAddress = { address ->
                                    onAction(CheckoutUiAction.OnAddShippingAddress(address))
                                    shownBottomSheet = ShownBottomSheet.SHIPPING_ADDRESS
                                },
                                onCancel = {
                                    shownBottomSheet = ShownBottomSheet.SHIPPING_ADDRESS
                                }
                            )
                        }

                        else -> Unit
                    }
                }
            }
        }

    }
}

private enum class ShownBottomSheet {
    NONE,
    SHIPPING_ADDRESS,
    ADD_ADDRESS,
    SHIPPING_TYPE
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CheckoutScreenContentPreview() {
    val addresses = listOf(
        ShippingAddress(
            id = "1",
            userId = "1",
            title = "Home",
            country = "Saudi Arabia",
            city = "Eastlake Village",
            addressLine = "Street 8, House 3",
            phoneNumber = "(968) 403-4498"
        ),
        ShippingAddress(
            id = "1",
            userId = "1",
            title = "Friend's House",
            country = "Saudi Arabia",
            city = "Eastlake Village",
            addressLine = "Street 8, House 3",
            phoneNumber = "(968) 403-4498"
        ),
        ShippingAddress(
            id = "1",
            userId = "1",
            title = "Work",
            country = "Saudi Arabia",
            city = "Eastlake Village",
            addressLine = "Street 8, House 3",
            phoneNumber = "(968) 403-4498"
        ),

    )
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
        CheckoutScreenContent(
            shippingAddress = addresses.first(),
            addresses = addresses,
            shippingType = ShippingType.REGULAR,
            cartItems = items,
            isLoading = false
        ) {}
    }
}
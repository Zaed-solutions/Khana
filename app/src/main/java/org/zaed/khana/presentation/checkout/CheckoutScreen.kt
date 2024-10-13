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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.model.ShippingType
import org.zaed.khana.presentation.checkout.components.AddAddressBottomSheet
import org.zaed.khana.presentation.checkout.components.OrderListSection
import org.zaed.khana.presentation.checkout.components.ShippingAddressBottomSheet
import org.zaed.khana.presentation.checkout.components.ShippingAddressSection
import org.zaed.khana.presentation.checkout.components.ShippingTypeBottomSheet
import org.zaed.khana.presentation.checkout.components.ShippingTypeSection

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
                    Text(text = stringResource(R.string.checkout))
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(CheckoutUiAction.OnBackPressed) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ),
                shadowElevation = 24.dp
            ) {
                Button(
                    onClick = { onAction(CheckoutUiAction.OnContinueToPaymentClicked) },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.continue_to_payment),
                        style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier
                .fillMaxWidth()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ShippingAddressSection(
                shippingAddress = shippingAddress,
                onChangeAddressClicked = { shownBottomSheet = ShownBottomSheet.SHIPPING_ADDRESS }
            )
            ShippingTypeSection(
                shippingType = shippingType,
                onChangeTypeClicked = { shownBottomSheet = ShownBottomSheet.SHIPPING_TYPE }
            )
            OrderListSection(cartItems = cartItems)
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

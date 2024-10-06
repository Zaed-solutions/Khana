package org.zaed.khana.presentation.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.R
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.model.ShippingType
import org.zaed.khana.presentation.checkout.components.CheckoutScreenSection
import org.zaed.khana.presentation.checkout.components.ShippingAddressSection
import org.zaed.khana.presentation.checkout.components.ShippingTypeSection

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    CheckoutScreenContent(
        shippingAddress = state.selectedShippingAddress,
        shippingType = state.selectedShippingType,
        onAction = {/*TODO*/}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CheckoutScreenContent(
    modifier: Modifier = Modifier,
    shippingAddress: ShippingAddress,
    shippingType: ShippingType,
    onAction: (CheckoutUiAction) -> Unit
) {
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
            Surface (
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                shadowElevation = 8.dp
            ) {
                Button(onClick = { /*TODO*/ }, modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()) {
                    Text(text = stringResource(R.string.continue_to_payment))
                }
            }
        }
    ) { paddingValues ->
        Column (
            modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ){
            //Shipping address
            ShippingAddressSection(
                shippingAddress = shippingAddress,
                onChangeAddressClicked = { /*TODO*/ }
            )
            //shipping type
            ShippingTypeSection(
                shippingType = shippingType,
                onChangeTypeClicked = { /*TODO*/ }
            )
            TODO()
        }

    }
}


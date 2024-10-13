package org.zaed.khana.presentation.checkout.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.data.model.ShippingAddress

@Composable
fun ShippingAddressBottomSheet(
    modifier: Modifier = Modifier,
    selectedAddress: ShippingAddress,
    addresses: List<ShippingAddress>,
    onChangeShippingAddress: (ShippingAddress) -> Unit,
    onAddNewAddressClicked: () -> Unit,
) {
    LazyColumn (
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(addresses.size) { index ->
            val address = addresses[index]
            ShippingAddressItem(shippingAddress = address) {
                RadioButton(
                    selected = selectedAddress.getDisplayAddress() == address.getDisplayAddress(),
                    onClick = {
                        onChangeShippingAddress(address)
                    }
                )
            }
        }
        item {
            FilledTonalButton(
                onClick = { onAddNewAddressClicked() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.add_new_shipping_address))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ShippingAddressBottomSheetPreview() {
    val addresses = listOf(
        ShippingAddress(
            title = "Home",
            country = "Egypt",
            city = "Cairo",
            addressLine = "El Haram",
            phoneNumber = "01000000000",
        ),
        ShippingAddress(
            title = "Work",
            country = "Egypt",
            city = "Cairo",
            addressLine = "El Maadi",
            phoneNumber = "01000000000",
        ),
        ShippingAddress(
            title = "Family's House",
            country = "Egypt",
            city = "Cairo",
            addressLine = "El Zamalek",
            phoneNumber = "01000000000",
        ),
    )
    ShippingAddressBottomSheet(
        selectedAddress = addresses[0],
        addresses = addresses,
        onChangeShippingAddress = {},
        onAddNewAddressClicked = {},
    )
}
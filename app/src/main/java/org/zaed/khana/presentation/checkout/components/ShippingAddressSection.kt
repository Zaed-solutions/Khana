package org.zaed.khana.presentation.checkout.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.zaed.khana.R
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ShippingAddressSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    shippingAddress: ShippingAddress,
    onChangeAddressClicked: () -> Unit,
    onAddShippingAddressClicked: () -> Unit,
) {
    CheckoutScreenSection(
        modifier = modifier,
        title = stringResource(R.string.choose_shipping_address)
    ) {
        if(!isLoading && shippingAddress.id.isBlank()){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.no_set_shipping_address),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                TextButton(
                    onClick = { onAddShippingAddressClicked() },
                ) {
                    Text(
                        text = stringResource(R.string.add),
                    )
                }
            }
        } else {
            ShippingAddressItem(
                isLoading = isLoading,
                shippingAddress = shippingAddress
            ) {
                TextButton(
                    onClick = { onChangeAddressClicked() },
                    enabled = !isLoading
                ) {
                    Text(text = stringResource(R.string.change))
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ShippingAddressSectionPreview() {
    KhanaTheme {
        val shippingAddress = ShippingAddress(
            title = "Home",
            country = "Egypt",
            city = "Cairo",
            addressLine = "Address Line",
            phoneNumber = "01000000000",
        )
        ShippingAddressSection(
            isLoading = false,
            shippingAddress = shippingAddress,
            onChangeAddressClicked = {},
            onAddShippingAddressClicked = {}
        )
    }
}
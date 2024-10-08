package org.zaed.khana.presentation.checkout.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.zaed.khana.R
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ShippingAddressSection(
    modifier: Modifier = Modifier,
    shippingAddress: ShippingAddress,
    onChangeAddressClicked: () -> Unit
) {
    CheckoutScreenSection(title = stringResource(R.string.shipping_address)) {
        ShippingAddressItem(shippingAddress = shippingAddress) {
            TextButton(onClick = { onChangeAddressClicked() }) {
                Text(text = stringResource(R.string.change))
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
            phoneNumber = "01000000000",
        )
        ShippingAddressSection(shippingAddress = shippingAddress) {

        }
    }
}
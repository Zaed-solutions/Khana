package org.zaed.khana.presentation.checkout.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
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
import io.ktor.util.valuesOf
import org.zaed.khana.R
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.data.model.ShippingType
import org.zaed.khana.data.model.getEstimatedDeliveryDate
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ShippingTypeSection(
    modifier: Modifier = Modifier,
    shippingType: ShippingType,
    onChangeTypeClicked: () -> Unit
) {
    CheckoutScreenSection(
        modifier = modifier.fillMaxWidth(),
        title = stringResource(R.string.shipping_address)
    ) {
        ShippingTypeItem(
            modifier = modifier.fillMaxWidth(),
            shippingType = shippingType,
            trailingContent = {
                TextButton(onClick = onChangeTypeClicked) {
                    Text(text = stringResource(R.string.change))
                }
            }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ShippingTypeSectionPreview() {
    KhanaTheme {
        val shippingType = ShippingType.ECONOMY
        ShippingTypeSection(shippingType = shippingType) {

        }
    }
}
package org.zaed.khana.presentation.checkout.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.zaed.khana.R
import org.zaed.khana.data.model.ShippingType
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ShippingTypeSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    shippingType: ShippingType,
    onChangeTypeClicked: () -> Unit
) {
    CheckoutScreenSection(
        modifier = modifier.fillMaxWidth(),
        title = stringResource(R.string.choose_shipping_type)
    ) {
        ShippingTypeItem(
            modifier = modifier.fillMaxWidth(),
            isLoading = isLoading,
            shippingType = shippingType,
            trailingContent = {
                TextButton(
                    onClick = onChangeTypeClicked,
                    enabled = !isLoading
                ) {
                    Text(
                        text = stringResource(R.string.change),
                        style = MaterialTheme.typography.bodyMedium
                    )
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
        ShippingTypeSection(isLoading = true,shippingType = shippingType) {

        }
    }
}
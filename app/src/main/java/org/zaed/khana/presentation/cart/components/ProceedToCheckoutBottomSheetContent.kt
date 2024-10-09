package org.zaed.khana.presentation.cart.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.toMoney

@Composable
fun ProceedToCheckoutBottomSheetContent(
    modifier: Modifier = Modifier,
    discountPercentage: Float,
    subTotalPrice: Float,
    deliveryFee: Float,
    onApplyPromoCode: (String) -> Unit,
    onProceedToCheckout: () -> Unit,
) {
    var code by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //promo code text field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 52.dp),
            shape = MaterialTheme.shapes.extraLarge,
            value = code,
            onValueChange = { code = it },
            placeholder = {
                Text(text = stringResource(R.string.promo_code))
            },
            trailingIcon = {
                TextButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { onApplyPromoCode(code) },
                ) {
                    Text(text = stringResource(R.string.apply))
                }
            })
        //price calculations
        PriceCalculationItem(
            modifier = Modifier.padding(top = 16.dp),
            title = "Sub-Total",
            price = subTotalPrice
        )
        PriceCalculationItem(title = "Delivery Fee", price = deliveryFee)
        AnimatedVisibility(visible = discountPercentage > 0) {
            PriceCalculationItem(title = "Discount", price = discountPercentage * subTotalPrice)
        }
        PriceCalculationItem(
            title = "Total Cost",
            price = subTotalPrice + deliveryFee - discountPercentage * subTotalPrice,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        //proceed button
        Button(
            onClick = { onProceedToCheckout() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.proceed_to_checkout))
        }
    }
}

@Composable
private fun PriceCalculationItem(
    modifier: Modifier = Modifier,
    title: String,
    price: Float
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)
        Text(text = price.toMoney())
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProceedToCheckoutBottomSheetContentPreview() {
    KhanaTheme {
        ProceedToCheckoutBottomSheetContent(
            discountPercentage = 0.1f,
            subTotalPrice = 407.94f,
            deliveryFee = 25f,
            onApplyPromoCode = {}
        ) {
        }
    }
}
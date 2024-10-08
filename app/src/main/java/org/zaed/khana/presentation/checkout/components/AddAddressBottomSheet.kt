package org.zaed.khana.presentation.checkout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun AddAddressBottomSheet(
    modifier: Modifier = Modifier,
    onAddAddress: (ShippingAddress) -> Unit,
    onCancel: () -> Unit
) {
    var address by remember { mutableStateOf(ShippingAddress()) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = address.title,
            onValueChange = {
                address = address.copy(title = it)
            },
            label = {
                Text(text = stringResource(R.string.title))
            },
            maxLines = 1,
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = address.country,
                onValueChange = {
                    address = address.copy(country = it)
                },
                label = {
                    Text(text = "Country")
                },
                maxLines = 1,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = modifier.weight(1f)
            )
            OutlinedTextField(
                value = address.city,
                onValueChange = {
                    address = address.copy(city = it)
                },
                label = {
                    Text(text = stringResource(R.string.city))
                },
                maxLines = 1,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = modifier.weight(1f)
            )
        }
        OutlinedTextField(
            value = address.addressLine,
            onValueChange = {
                address = address.copy(addressLine = it)
            },
            label = {
                Text(text = stringResource(R.string.address))
            },
            maxLines = 1,
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = address.phoneNumber,
            onValueChange = {
                address = address.copy(phoneNumber = it)
            },
            label = {
                Text(text = stringResource(R.string.phone_number))
            },
            maxLines = 1,
            shape = MaterialTheme.shapes.extraLarge,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { onCancel() },
                modifier = modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.cancel))
            }
            Button(
                onClick = { onAddAddress(address) },
                modifier = modifier.weight(1f)
            ) {
                Text(stringResource(R.string.add))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun AddAddressBottomSheetPreview() {
    KhanaTheme {
        AddAddressBottomSheet(onAddAddress = {}) {}
    }
}
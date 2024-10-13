package org.zaed.khana.presentation.checkout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.zaed.khana.R
import org.zaed.khana.data.model.ShippingAddress
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun AddAddressBottomSheet(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onAddAddress: (ShippingAddress) -> Unit,
    onCancel: () -> Unit
) {
    var address by remember { mutableStateOf(ShippingAddress()) }
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    var scrollToBottom by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LaunchedEffect(key1 = scrollToBottom) {
            coroutineScope.launch {
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }
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
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequester1.requestFocus() }
            )
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
                modifier = modifier
                    .weight(1f)
                    .focusRequester(focusRequester1),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester2.requestFocus() }
                )
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
                modifier = modifier
                    .weight(1f)
                    .focusRequester(focusRequester2),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequester3.requestFocus()
                        scrollToBottom = true
                    }
                )
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
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester3),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester4.requestFocus()
                    scrollToBottom = true
                }
            )
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
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester4),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onAddAddress(address)
                }
            )
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
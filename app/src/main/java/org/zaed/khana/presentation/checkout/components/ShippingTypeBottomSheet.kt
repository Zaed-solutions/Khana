package org.zaed.khana.presentation.checkout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.ShippingType
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ShippingTypeBottomSheet(
    modifier: Modifier = Modifier,
    selectedType: ShippingType,
    onChangeShippingType: (ShippingType) -> Unit,
    ){
    LazyColumn (
        modifier = modifier.fillMaxWidth().padding(bottom = 16.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ShippingType.entries.size) { index ->
            val type = ShippingType.entries[index]
            ShippingTypeItem(isLoading = false, shippingType = type) {
                RadioButton(
                    selected = selectedType == type,
                    onClick = {
                        onChangeShippingType(type)
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ShippingTypeBottomSheetPreview() {
    KhanaTheme {
        Column{
            ShippingTypeBottomSheet(selectedType = ShippingType.REGULAR, onChangeShippingType = {})
            HorizontalDivider()
        }
    }
}
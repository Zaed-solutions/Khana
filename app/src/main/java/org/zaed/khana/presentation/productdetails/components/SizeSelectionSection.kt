package org.zaed.khana.presentation.productdetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.home.components.ChipItem
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun SizeSelectionSection(
    modifier: Modifier = Modifier,
    availableSizes:List<String>,
    selectedSize: String,
    onSelectSize: (String) -> Unit
) {
    Column{
        Text(
            text = stringResource(R.string.select_size),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(availableSizes.size) { index ->
                val size = availableSizes[index]
                ChipItem(
                    title = size,
                    isSelected = size == selectedSize,
                    onSelectItem = onSelectSize,
                    shape = MaterialTheme.shapes.small
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SizeSelectionSectionPreview() {
    KhanaTheme {
        SizeSelectionSection(availableSizes = listOf("S", "M", "L", "XL", "XXL", "XXL"), selectedSize = "L") {

        }
    }
}
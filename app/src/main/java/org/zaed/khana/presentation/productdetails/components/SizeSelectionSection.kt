package org.zaed.khana.presentation.productdetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun SizeSelectionSection(
    modifier: Modifier = Modifier,
    availableSizes:List<String>,
    selectedSize: String,
    isLoading: Boolean,
    onSelectSize: (String) -> Unit,
) {
    if(isLoading){
        SizeSelectionShimmer(modifier)
    } else {
        Column(
            modifier = modifier
        ){
            Text(
                text = stringResource(R.string.select_size),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyRow(
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
}

@Composable
private fun SizeSelectionShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ){
        Text(
            text = stringResource(R.string.select_size),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Box(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().height(32.dp).shimmerEffect()
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SizeSelectionSectionPreview() {
    KhanaTheme {
        SizeSelectionSection(
            availableSizes = listOf("S", "M", "L", "XL", "XXL", "XXL"),
            selectedSize = "L",
            isLoading = true
        ) {

        }
    }
}
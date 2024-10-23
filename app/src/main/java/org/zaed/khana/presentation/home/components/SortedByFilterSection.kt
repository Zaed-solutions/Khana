package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun SortedByFilterSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    sortedByOption: List<String>,
    selectedOption: String,
    onSelectOption: (String) -> Unit
) {
    if(isLoading){
        SortedByFilterSectionShimmer()
    } else {
        LazyRow(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sortedByOption.size) { index ->
                val option = sortedByOption[index]
                ChipItem(
                    modifier = Modifier.animateItem(),
                    title = option,
                    isSelected = option == selectedOption,
                    onSelectItem = onSelectOption
                )
            }
        }
    }
}

@Composable
private fun SortedByFilterSectionShimmer(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(5) {
            Box(
                modifier = Modifier
                    .size(width = 84.dp, height = 32.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .shimmerEffect()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SortedByFilterSectionPreview() {
    KhanaTheme {
        SortedByFilterSection(
            isLoading = false,
            sortedByOption = listOf("Label 1", "Label 2", "Label 3", "Label 4", "Label 5"),
            selectedOption = "Label 1",
            onSelectOption = {})
    }
}
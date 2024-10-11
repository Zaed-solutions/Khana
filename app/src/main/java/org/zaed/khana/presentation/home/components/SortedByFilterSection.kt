package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun SortedByFilterSection(
    sortedByOption: List<String>,
    selectedOption: String,
    onSelectOption: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sortedByOption.size) { index ->
            val option = sortedByOption[index]
            ChipItem(
                title = option,
                isSelected = option == selectedOption,
                onSelectItem = onSelectOption
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SortedByFilterSectionPreview() {
    KhanaTheme {
        SortedByFilterSection(
            sortedByOption = listOf("Label 1", "Label 2", "Label 3", "Label 4", "Label 5"),
            selectedOption = "Label 1",
            onSelectOption = {})
    }
}
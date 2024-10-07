package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun LabelFilterSection(
    labels: List<String>,
    selectedLabel: String,
    onSelectLabel: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(labels.size) { index ->
            val label = labels[index]
            ChipItem(
                title = label,
                isSelected = label == selectedLabel,
                onSelectItem = onSelectLabel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LabelFilterSectionPreview() {
    KhanaTheme {
        LabelFilterSection(
            labels = listOf("Label 1", "Label 2", "Label 3", "Label 4", "Label 5"),
            selectedLabel = "Label 1",
            onSelectLabel = {})
    }
}
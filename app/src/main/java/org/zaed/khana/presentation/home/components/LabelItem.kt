package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun LabelItem(
    label: String,
    isSelected: Boolean,
    onSelectLabel: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        modifier = modifier,
        colors = if (isSelected) FilterChipDefaults.filterChipColors(
            selectedContainerColor =  MaterialTheme.colorScheme.primary,
            selectedLabelColor =  MaterialTheme.colorScheme.onPrimary
        ) else FilterChipDefaults.filterChipColors(),
        selected = isSelected,
        shape = MaterialTheme.shapes.extraLarge,
        onClick = {
            onSelectLabel(label)
        },
        label = {
            Text(
                text = label,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    )
}

@Preview(showBackground = true, name = "Selected Label")
@Composable
private fun SelectedLabelItemPreview() {
    KhanaTheme {
        LabelItem(label = "Test", isSelected = true, onSelectLabel = {})
    }
}

@Preview(showBackground = true, name = "Unselected Label")
@Composable
private fun UnselectedLabelItemPreview() {
    KhanaTheme {
        LabelItem(label = "Test", isSelected = false, onSelectLabel = {})
    }
}
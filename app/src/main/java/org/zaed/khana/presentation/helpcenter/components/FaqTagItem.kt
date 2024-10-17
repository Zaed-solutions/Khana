package org.zaed.khana.presentation.helpcenter.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FaqTagItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    tag: String,
    onTagClicked: (String) -> Unit
) {
    FilterChip(
        shape = MaterialTheme.shapes.extraLarge,
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderWidth = 0.dp,
            borderColor = Color.Transparent
        ),
        selected = isSelected,
        onClick = {
            onTagClicked(tag)
        },
        label = {
            Text(
                text = tag,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        )
    )
}
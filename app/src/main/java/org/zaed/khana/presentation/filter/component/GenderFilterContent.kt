package org.zaed.khana.presentation.filter.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.util.GenderFilterOption
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun GenderFilterContent(
    modifier: Modifier = Modifier,
    selectedGender: GenderFilterOption,
    onGenderSelected: (GenderFilterOption) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(GenderFilterOption.entries.size) { index ->
            val gender = GenderFilterOption.entries[index]
            val isSelected = gender == selectedGender
            FilterChip(
                selected = isSelected,
                onClick = { onGenderSelected(gender) },
                label = { Text(text = gender.displayName) },
                shape = MaterialTheme.shapes.extraLarge,
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderWidth = 0.dp,
                    borderColor = Color.Transparent
                ),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun GenderFilterContentPreview() {
    KhanaTheme {
        GenderFilterContent(selectedGender = GenderFilterOption.ALL) {

        }
    }
}
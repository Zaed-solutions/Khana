package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import org.zaed.khana.data.model.Color
import org.zaed.khana.presentation.productdetails.components.ColorItem
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ColorSelectionSection(
    modifier: Modifier = Modifier,
    availableColors: List<Color>,
    selectedColor: Color,
    onSelectColor: (String) -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.select_color),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = selectedColor.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(availableColors.size) { index ->
                val color = availableColors[index]
                ColorItem(
                    hexColor = color.hex,
                    isSelected = color.hex == selectedColor.hex,
                    onSelectColor = {
                        onSelectColor(color.hex)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ColorSelectionSectionPreview() {
    val colors = listOf(
        Color("Black", "#000000"),
        Color("Red", "#FF0000"),
        Color("Green", "#00FF00"),
        Color("Blue", "#0000FF"),
        Color("Yellow", "#FFFF00"),
        Color("Cyan", "#00FFFF"),
        Color("Magenta", "#FF00FF"),
        Color("Gray", "#808080"),
        Color("Orange", "#FFA500"),
        Color("Pink", "#FFC0CB"),
        Color("Purple", "#800080"),
        Color("Brown", "#A52A2A"),
        Color("Beige", "#F5F5DC"),
        Color("LightGray", "#D3D3D3"),
        Color("DarkGray", "#A9A9A9"),
        Color("LightBlue", "#ADD8E6"),
        Color("DarkBlue", "#00008B"),
        Color("LightGreen", "#90EE90"),
        Color("DarkGreen", "#006400"),
        Color("LightPink", "#FFB6C1"),
        Color("DarkRed", "#8B0000"),
    )
    KhanaTheme {
        ColorSelectionSection(
            availableColors = colors,
            selectedColor = Color("Yellow", "#FFFF00")
        ) {

        }
    }

}


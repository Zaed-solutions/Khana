package org.zaed.khana.presentation.productdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.util.hex
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.fromHex

@Composable
fun ColorItem(
    modifier: Modifier = Modifier,
    hexColor: String,
    isSelected: Boolean,
    onSelectColor: () -> Unit
) {
    val color = Color.fromHex(hexColor)
    Icon(
        imageVector = if(isSelected) Icons.Default.CheckCircle else Icons.Default.Circle,
        contentDescription = null,
        tint = color,
        modifier = modifier.size(48.dp).clickable { onSelectColor() }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ColorItemPreview() {
    KhanaTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ColorItem(hexColor = "#00ffff", isSelected = true) {

            }
            ColorItem(hexColor = "#00ffff", isSelected = false) {

            }
        }
    }

}
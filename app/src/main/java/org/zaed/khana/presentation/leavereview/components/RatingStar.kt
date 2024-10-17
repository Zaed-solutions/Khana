package org.zaed.khana.presentation.leavereview.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingStar(
    modifier: Modifier = Modifier,
    rating: Int,
    index: Int,
    onRatingChanged: (Int) -> Unit
) {
    val imageVector = if (rating >= index + 1) {
        Icons.Default.Star
    } else {
        Icons.Default.StarOutline
    }
    IconButton(
        modifier = modifier,
        onClick = { onRatingChanged(index + 1) }
    ) {
        Icon(
            imageVector = imageVector,
            tint = Color.Yellow,
            modifier = Modifier.size(36.dp),
            contentDescription = null
        )
    }
}
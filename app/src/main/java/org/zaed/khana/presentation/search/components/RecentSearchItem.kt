package org.zaed.khana.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun RecentSearchItem(
    modifier: Modifier = Modifier,
    item: String,
    onItemClick: (String) -> Unit,
    onDeleteItem: (String) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }) {
        Text(
            text = item,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .focusable(false)
        )
        IconButton(onClick = { onDeleteItem(item) }) {
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun RecentSearchItemShimmer(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(24.dp)
                .shimmerEffect()

        )
        IconButton(
            onClick = {},
            enabled = false
        ) {
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun RecentSearchItemPreview() {
    KhanaTheme {
        RecentSearchItem(
            item = "Chicken",
            onItemClick = {},
            onDeleteItem = {},
        )
    }
}
package org.zaed.khana.presentation.search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecentSearchesSection(
    modifier: Modifier = Modifier,
    items: List<String>,
    onItemClick: (String) -> Unit,
    onDeleteItem: (String) -> Unit,
    onClearAllRecentSearches: () -> Unit
) {
    if (items.isNotEmpty()) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
        ) {
            stickyHeader {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.recent),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { onClearAllRecentSearches() }) {
                            Text(text = stringResource(R.string.clear_all), style = MaterialTheme.typography.titleMedium)
                        }
                    }
                    HorizontalDivider(thickness = 1.dp)
                }

            }
            items(items) { item ->
                RecentSearchItem(
                    modifier = Modifier.animateItem(),
                    item = item,
                    onItemClick = onItemClick,
                    onDeleteItem = onDeleteItem
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun RecentSearchesPreview() {
    KhanaTheme {
        RecentSearchesSection(
            items = listOf("Chicken", "Beef", "Pasta"),
            onItemClick = {},
            onDeleteItem = {},
            onClearAllRecentSearches = {}
        )
    }
}
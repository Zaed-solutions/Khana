package org.zaed.khana.presentation.filter.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.zaed.khana.R

@Composable
fun FilterBottomBar(
    modifier: Modifier = Modifier,
    onApplyFilters: () -> Unit,
    onResetFilters: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        FilledTonalButton(
            modifier = Modifier.widthIn(min = 150.dp),
            onClick = { onResetFilters() }) {
            Text(text = stringResource(R.string.reset_filters))
        }
        Button(
            modifier = Modifier.widthIn(min = 150.dp),
            onClick = { onApplyFilters() }
        ) {
            Text(stringResource(R.string.apply))
        }
    }
}
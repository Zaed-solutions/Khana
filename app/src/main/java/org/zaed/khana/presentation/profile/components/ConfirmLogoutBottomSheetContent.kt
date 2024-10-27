package org.zaed.khana.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun ConfirmLogoutBottomSheetContent(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.logout),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )
        HorizontalDivider(thickness = 0.5.dp)
        Text(
            text = stringResource(R.string.are_you_sure_you_want_to_logout),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilledTonalButton(
                onClick = { onCancel() },
                modifier = Modifier.widthIn(min = 150.dp)
            ) {
                Text(stringResource(R.string.cancel))
            }
            Button(
                onClick = { onConfirm() },
                modifier = Modifier.widthIn(min = 150.dp)
            ) {
                Text(text = stringResource(R.string.yes_logout))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ConfirmLogoutPreview() {
    KhanaTheme {
        ConfirmLogoutBottomSheetContent(
            onConfirm = {},
            onCancel = {}
        )
    }
}
package org.zaed.khana.presentation.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.zaed.khana.R

@Composable
fun OrSignInWithText(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        HorizontalDivider(Modifier.align(Alignment.Center))
        Text(
            stringResource(R.string.or_sign_in_with),
            Modifier
                .align(Alignment.Center)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 4.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.8f)
        )
    }
}
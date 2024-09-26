package org.zaed.khana.presentation.auth.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.zaed.khana.R

@Composable
fun SignUpWelcomeSentence(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.fill_the_information_below_or_register_with_social_media_account),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.8f),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        textAlign = TextAlign.Center
    )
}
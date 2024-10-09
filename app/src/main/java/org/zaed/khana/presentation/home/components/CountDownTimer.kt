package org.zaed.khana.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun CountDownTimer(
    timeLeftInSeconds: Long,
    modifier: Modifier = Modifier
) {
    val hours = timeLeftInSeconds / 3600
    val minutes = (timeLeftInSeconds % 3600) / 60
    val remainingSeconds = timeLeftInSeconds % 60
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Closing in:")
        Text(text = hours.toString().padStart(2, '0'),
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
        Text(text = ":")
        Text(text = minutes.toString().padStart(2, '0'),
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
        Text(text = ":")
        Text(text = remainingSeconds.toString().padStart(2, '0'),
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CountDownTimerPreview() {
    KhanaTheme {
        CountDownTimer(timeLeftInSeconds = 8006L, modifier = Modifier.padding(16.dp))
    }
}

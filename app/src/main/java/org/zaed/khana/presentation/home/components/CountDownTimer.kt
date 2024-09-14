package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text=hours.toString().padStart(2, '0'))
        Text(text=":")
        Text(text=minutes.toString().padStart(2, '0'))
        Text(text=":")
        Text(text=remainingSeconds.toString().padStart(2, '0'))
    }
}

@Preview(showBackground = true)
@Composable
private fun CountDownTimerPreview() {
    KhanaTheme {
        CountDownTimer(timeLeftInSeconds = 8006L)
    }
}

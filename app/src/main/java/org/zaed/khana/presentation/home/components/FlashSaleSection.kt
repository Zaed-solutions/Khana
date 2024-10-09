package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun FlashSaleSection(
    endsAtEpochSeconds: Long,
    modifier: Modifier = Modifier
) {
    var timeLeft by remember {
        val t = endsAtEpochSeconds - Clock.System.now().epochSeconds
        mutableLongStateOf(if (t > 0) t else 0)
    }
    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
    }
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.flash_sale),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        CountDownTimer(timeLeft)
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun FlashSaleSectionPreview() {
    KhanaTheme {
        FlashSaleSection(endsAtEpochSeconds = Clock.System.now().epochSeconds + 5000L)
    }
}
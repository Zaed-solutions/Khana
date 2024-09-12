package org.zaed.khana.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
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
        mutableLongStateOf(endsAtEpochSeconds - Clock.System.now().epochSeconds)
    }
    LaunchedEffect(key1 = timeLeft) {
        delay(1000)
        timeLeft--
    }
    Row (
        modifier = modifier.fillMaxWidth()
    ){
        Text(text = stringResource(R.string.flash_sale))
        Spacer(modifier = Modifier.weight(1f))
        CountDownTimer(timeLeft)
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun FlashSaleSectionPreview() {
    KhanaTheme {
        FlashSaleSection(endsAtEpochSeconds = Clock.System.now().epochSeconds+5000L)
    }
}
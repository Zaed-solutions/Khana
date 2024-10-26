package org.zaed.khana.presentation.trackorder.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import org.zaed.khana.R
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.formatEpochSecondsToDate
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun OrderDetailsSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    expectedDeliveryEpochSeconds: Long,
    trackingId: String
) {
    val formattedDate = expectedDeliveryEpochSeconds.formatEpochSecondsToDate()
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.order_details),
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.expected_delivery_date),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.weight(1f)
            )
            if(isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(20.dp)
                        .shimmerEffect()
                )
            } else {
                Text(
                    text = formattedDate
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.tracking_id),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.weight(1f)
            )
            if(isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(20.dp)
                        .shimmerEffect()
                )
            } else {
                Text(
                    text = trackingId
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun OrderDetailsPreview() {
    KhanaTheme {
        OrderDetailsSection(
            isLoading = true,
            expectedDeliveryEpochSeconds = Clock.System.now().epochSeconds - 6000,
            trackingId = "TRK1234567890"
        )
    }
}
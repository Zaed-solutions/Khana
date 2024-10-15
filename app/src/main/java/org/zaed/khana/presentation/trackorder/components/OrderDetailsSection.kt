package org.zaed.khana.presentation.trackorder.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.util.formatEpochSecondsToDate

@Composable
fun OrderDetailsSection(
    modifier: Modifier = Modifier,
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
                modifier = Modifier.weight(1f)
            )
            Text(
                text = formattedDate
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.tracking_id),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = trackingId
            )
        }
    }
}
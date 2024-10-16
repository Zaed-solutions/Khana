package org.zaed.khana.presentation.trackorder.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import org.zaed.khana.R
import org.zaed.khana.data.model.OrderStatus
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.util.formatEpochSecondsToDateTime

@Composable
fun OrderStatusSection(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
    confirmedEpochSeconds: Long,
    shippedEpochSeconds: Long,
    deliveredEpochSeconds: Long,
) {
    val enabledColor = MaterialTheme.colorScheme.primary
    val disabledColor = MaterialTheme.colorScheme.secondaryContainer
    val progressPercent = when (orderStatus) {
        OrderStatus.CONFIRMED -> 0.33f
        OrderStatus.SHIPPED -> 0.67f
        OrderStatus.DELIVERED -> 0.999f
        else -> 0f
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.order_status),
            style = MaterialTheme.typography.titleLarge
        )
        Box (
            modifier = Modifier.fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .padding(start = 26.dp)
                    .padding(vertical = 44.dp)
                    .width(4.dp)
                    .height(240.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(progressPercent)
                        .background(enabledColor)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1 - progressPercent)
                        .background(disabledColor)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ListItem(
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                            tint = enabledColor
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.order_placed),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingContent = {
                        Text(
                            text = confirmedEpochSeconds.formatEpochSecondsToDateTime(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Assignment,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                ListItem(
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                            tint = enabledColor
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.in_progress),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingContent = {
                        Text(
                            text = confirmedEpochSeconds.formatEpochSecondsToDateTime(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                ListItem(
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                            tint = if (orderStatus == OrderStatus.SHIPPED || orderStatus == OrderStatus.DELIVERED) enabledColor else disabledColor
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.shipped),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingContent = {
                        Text(
                            text = shippedEpochSeconds.formatEpochSecondsToDateTime(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.LocalShipping,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                ListItem(
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                            tint = if (orderStatus == OrderStatus.DELIVERED) enabledColor else disabledColor
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.delivered),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingContent = {
                        Text(
                            text = deliveredEpochSeconds.formatEpochSecondsToDateTime(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.Receipt,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun OrderStatusPreview() {
    KhanaTheme {
        OrderStatusSection(
            orderStatus = OrderStatus.DELIVERED,
            confirmedEpochSeconds = Clock.System.now().epochSeconds - 15000,
            shippedEpochSeconds = Clock.System.now().epochSeconds + 5000,
            deliveredEpochSeconds = Clock.System.now().epochSeconds + 15000
        )
    }
}
package org.zaed.khana.presentation.trackorder.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun OrderStatusSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    orderStatus: OrderStatus,
    confirmedEpochSeconds: Long,
    shippedEpochSeconds: Long,
    deliveredEpochSeconds: Long,
) {
    val enabledColor = MaterialTheme.colorScheme.primary
    val disabledColor = MaterialTheme.colorScheme.secondaryContainer
    var progressPercent by remember {
        mutableFloatStateOf(0.001f)
    }
    LaunchedEffect(orderStatus, isLoading) {
        if(!isLoading){
            progressPercent = when (orderStatus) {
                OrderStatus.CONFIRMED -> 0.33f
                OrderStatus.SHIPPED -> 0.67f
                OrderStatus.DELIVERED -> 0.999f
                else -> 0.001f
            }
        }
    }
    val animatedProgress = animateFloatAsState(
        targetValue = progressPercent,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        ),
        label = "progress percentage"
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.order_status),
            style = MaterialTheme.typography.titleLarge
        )
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
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
                        .weight(animatedProgress.value)
                        .background(enabledColor)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1 - animatedProgress.value)
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
                        if (isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(20.dp)
                                    .shimmerEffect()
                            )
                        } else {
                            Text(
                                text = if(confirmedEpochSeconds == 0L) "TBD" else confirmedEpochSeconds.formatEpochSecondsToDateTime(),
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
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
                            tint = if (animatedProgress.value >= 0.33f) enabledColor else disabledColor
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.in_progress),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingContent = {
                        if(isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(20.dp)
                                    .shimmerEffect()
                            )
                        } else {
                            Text(
                                text = if(confirmedEpochSeconds == 0L) "TBD" else confirmedEpochSeconds.formatEpochSecondsToDateTime(),
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
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
                            tint = if (animatedProgress.value >= 0.67f) enabledColor else disabledColor
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.shipped),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingContent = {
                        if(isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(20.dp)
                                    .shimmerEffect()
                            )
                        } else {
                            Text(
                                text = if(shippedEpochSeconds == 0L) "TBD" else shippedEpochSeconds.formatEpochSecondsToDateTime(),
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
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
                            tint = if (animatedProgress.value >= 0.9f) enabledColor else disabledColor
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.delivered),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    supportingContent = {
                        if(isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(20.dp)
                                    .shimmerEffect()
                            )
                        } else {
                            Text(
                                text = if(deliveredEpochSeconds == 0L) "TBD" else deliveredEpochSeconds.formatEpochSecondsToDateTime(),
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
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
//            isLoading = true,
            confirmedEpochSeconds = Clock.System.now().epochSeconds - 15000,
            shippedEpochSeconds = Clock.System.now().epochSeconds + 5000,
            deliveredEpochSeconds = Clock.System.now().epochSeconds + 15000
        )
    }
}
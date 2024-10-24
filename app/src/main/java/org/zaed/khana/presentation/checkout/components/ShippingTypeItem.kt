package org.zaed.khana.presentation.checkout.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.zaed.khana.data.model.ShippingType
import org.zaed.khana.data.model.getEstimatedDeliveryDate
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun ShippingTypeItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    shippingType: ShippingType,
    trailingContent: @Composable () -> Unit,
    ){
    ListItem(
        modifier = modifier.fillMaxWidth(),
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        headlineContent = {
            Crossfade(isLoading, label = "shipping type"){ state ->
                when(state){
                    true -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                                .shimmerEffect()
                        )
                    }
                    false -> {
                        Text(
                            text = shippingType.title,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1
                        )
                    }
                }
            }
        },
        supportingContent = {
            Crossfade(isLoading, label = "") { state ->
                when (state) {
                    true -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(top = 8.dp)
                                .height(20.dp)
                                .shimmerEffect()
                        )
                    }

                    false -> {
                        Text(
                            text = shippingType.getEstimatedDeliveryDate(),
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2
                        )
                    }
                }
            }
        },
        leadingContent = {
            Icon(imageVector = Icons.Default.LocalShipping, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        },
        trailingContent = {
            trailingContent()
        }
    )
}
package org.zaed.khana.presentation.checkout.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.zaed.khana.R
import org.zaed.khana.data.model.ShippingAddress

@Composable
fun ShippingAddressItem(
    modifier: Modifier = Modifier,
    shippingAddress: ShippingAddress,
    trailingContent: @Composable () -> Unit,
) {
    ListItem(
        modifier = modifier.fillMaxWidth(),
        headlineContent = {
            Text(
                text = shippingAddress.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1
            )
        },
        supportingContent = {
            Text(
                text = shippingAddress.getDisplayAddress(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
        },
        leadingContent = {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        },
        trailingContent = {
            trailingContent()
        }
    )

}
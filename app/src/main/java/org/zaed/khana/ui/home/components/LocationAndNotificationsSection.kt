package org.zaed.khana.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.ui.theme.KhanaTheme

@Composable
fun LocationAndNotificationsSection(
    modifier: Modifier = Modifier,
    onNotificationsButtonClicked: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(R.string.location)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocationOn, contentDescription = null
                )
                Text(text = "New York, USA")
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onNotificationsButtonClicked() }) {
            Icon(
                imageVector = Icons.Rounded.Notifications, contentDescription = "notifications"
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun LocationAndNotificationsSectionPreview() {
    KhanaTheme {
        LocationAndNotificationsSection(modifier = Modifier.padding(horizontal = 16.dp))
    }
}
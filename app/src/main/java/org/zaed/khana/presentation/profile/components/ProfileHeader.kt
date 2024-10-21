package org.zaed.khana.presentation.profile.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    name: String,
    avatarUrl: String,
    avatarUri: Uri?,
    onAvatarPicked: (Uri) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ModifiableAvatar(
            avatarURL = avatarUrl,
            avatarUri = avatarUri,
            isModifiable = true,
            onImagePicked = onAvatarPicked
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            )
    }
}
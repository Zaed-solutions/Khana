package org.zaed.khana.presentation.profile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.zaed.khana.R
import org.zaed.khana.presentation.components.StatefulAsyncImage

@Composable
fun ModifiableAvatar(
    modifier: Modifier = Modifier,
    avatarURL: String,
    avatarUri: Uri?,
    isModifiable: Boolean,
    onImagePicked: (Uri) -> Unit,
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onImagePicked(uri)
        }
    }
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxWidth()
    ) {
        val imageModifier = Modifier
            .size(100.dp)
            .align(Alignment.Center)
            .clip(CircleShape)
        if (avatarUri == null && avatarURL.isBlank()) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null,
                modifier = imageModifier,
                tint = MaterialTheme.colorScheme.outline
            )
        } else if (avatarUri != null) {
            Image(
                painter = rememberAsyncImagePainter(avatarUri),
                modifier = imageModifier,
                contentDescription = stringResource(R.string.user_avatar),
                contentScale = ContentScale.Crop
            )
        } else {
            StatefulAsyncImage(
                modifier = imageModifier,
                imageUrl = avatarURL,
            )
        }
        if (isModifiable) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(32.dp)
                    .offset(x = 34.dp, y = 34.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .border(
                        BorderStroke(2.dp, MaterialTheme.colorScheme.surface),
                        CircleShape
                    )
                    .padding(6.dp)
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    }
            )
        }
    }
}
package org.zaed.khana.presentation.profile.components

import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.util.shimmerEffect

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    name: String,
    avatarUrl: String,
    avatarUri: Uri?,
    onAvatarPicked: (Uri) -> Unit,
) {
    Crossfade(targetState = isLoading, label = "profile header") { state ->
        when{
            state -> {
                ProfileHeaderShimmer()
            }
            else -> {
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
        }
    }
}

@Composable
private fun ProfileHeaderShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.size(100.dp).clip(CircleShape).shimmerEffect()
        )
        Box(
            modifier = Modifier.fillMaxWidth(0.6f).padding(top = 8.dp).height(24.dp).shimmerEffect()
        )
    }
}
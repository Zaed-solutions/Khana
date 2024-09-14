package org.zaed.khana.presentation.Login.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.zaed.khana.R
import org.zaed.khana.presentation.Login.LoginUIAction

@Composable
fun SocialMediaIconsRow(
    modifier: Modifier = Modifier,
    onSignInWithGoogleClicked: () -> Unit,
    onSignInWithFacebookClicked: () -> Unit,
    onSignInWithMicrosoftClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth().then(modifier),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        SocialMediaIconButton(
            icon = R.drawable.facebook,
            onClick = onSignInWithFacebookClicked
        )
        SocialMediaIconButton(
            icon = R.drawable.google,
            onClick = onSignInWithGoogleClicked

        )
        SocialMediaIconButton(
            icon = R.drawable.microsoft,
            onClick = onSignInWithMicrosoftClicked
        )

    }
}
package org.zaed.khana.presentation.Login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.zaed.khana.R
import org.zaed.khana.presentation.Login.LoginUIAction

@Composable
fun SocialMediaIconButton(
    modifier: Modifier = Modifier,
    icon: Int, action: (LoginUIAction) -> Unit,
) {
    IconButton(
        onClick = {
            when (icon) {
                R.drawable.google -> action(LoginUIAction.OnLoginWithGoogleClicked)
                R.drawable.facebook -> action(LoginUIAction.OnLoginWithFacebookClicked)
                R.drawable.microsoft -> action(LoginUIAction.OnLoginWithMicrosoftClicked)
            }
        },
        modifier = modifier
            .size(70.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), CircleShape)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = stringResource(R.string.sign_in_with_social_account),
            modifier = Modifier.padding(12.dp)
        )
    }
}
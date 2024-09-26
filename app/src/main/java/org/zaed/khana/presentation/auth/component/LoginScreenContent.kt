package org.zaed.khana.presentation.auth.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.auth.login.LoginUIAction
import org.zaed.khana.presentation.auth.login.LoginUiState

@Composable
fun LoginScreenContent(
    state: LoginUiState,
    action: (LoginUIAction) -> Unit,
    navigateToForgetPassword: () -> Unit,
    navigateToSignUp: () -> Unit,
    onSignInWithGoogleClicked: () -> Unit,
    onSignInWithFacebookClicked: () -> Unit = {},
    onSignInWithMicrosoftClicked: () -> Unit = {},
) {

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 32.dp),
        ) {
            Spacer(modifier = Modifier.weight(0.05f))
            SignInTitle(modifier = Modifier.weight(0.05f))
            SignInWelcomeSentence(modifier = Modifier.weight(0.1f))
            EmailTextField(
                modifier = Modifier.weight(0.1f),
                value = state.email,
                onValueChanged = { action(LoginUIAction.OnEmailChanged(it)) },
            )
            PasswordTextField(
                modifier = Modifier.weight(0.1f),
                value = state.password,
                onValueChanged = { action(LoginUIAction.OnPasswordChanged(it)) },
            )
            ForgetPasswordTextButton(
                modifier = Modifier.weight(0.1f),
                onClick = navigateToForgetPassword
            )
            SignInButton(onClick ={ action(LoginUIAction.OnSignInClicked)})
            OrSignInWithText(modifier = Modifier.weight(0.1f))
            SocialMediaIconsRow(
                modifier = Modifier.weight(0.1f),
                onSignInWithGoogleClicked = onSignInWithGoogleClicked,
                onSignInWithFacebookClicked = onSignInWithFacebookClicked,
                onSignInWithMicrosoftClicked = onSignInWithMicrosoftClicked
            )
            SignUpTextButton(modifier = Modifier.weight(0.1f), onClick = navigateToSignUp)
        }

    }
}
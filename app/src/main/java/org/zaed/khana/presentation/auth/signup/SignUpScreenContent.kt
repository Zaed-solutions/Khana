package org.zaed.khana.presentation.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.auth.component.EmailTextField
import org.zaed.khana.presentation.auth.component.NameTextField
import org.zaed.khana.presentation.auth.component.OrSignUpWithText
import org.zaed.khana.presentation.auth.component.PasswordTextField
import org.zaed.khana.presentation.auth.component.SignInTextButton
import org.zaed.khana.presentation.auth.component.SignUpButton
import org.zaed.khana.presentation.auth.component.SignUpTitle
import org.zaed.khana.presentation.auth.component.SignUpWelcomeSentence
import org.zaed.khana.presentation.auth.component.SocialMediaIconsRow
import org.zaed.khana.presentation.auth.component.TermsAndConditions
import org.zaed.khana.ui.theme.KhanaTheme

@Composable
fun SignUpScreenContent(
    state: SignUpUiState,
    action: (SignUpUIAction) -> Unit,
    navigateToSignIn: () -> Unit,
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
            Column(
                modifier = Modifier.weight(0.25f),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                SignUpTitle()
                SignUpWelcomeSentence()
            }
            Column(
            ) {
                NameTextField(
                    value = state.name,
                    onValueChanged = { action(SignUpUIAction.OnNameChanged(it)) },
                )
                EmailTextField(
                    value = state.email,
                    onValueChanged = { action(SignUpUIAction.OnEmailChanged(it)) },
                )
                PasswordTextField(
                    value = state.password,
                    onValueChanged = { action(SignUpUIAction.OnPasswordChanged(it)) },
                )
            }
            TermsAndConditions(
                modifier = Modifier.weight(0.1f),
                state = state,
                termsAndConditionsApproved = { action(SignUpUIAction.OnTermsAndConditionsClicked(it)) },
                onShowTermsAndConditions = {
                    //TODO: Navigate to terms and conditions
                }
            )
            SignUpButton(onClick = { action(SignUpUIAction.OnSignUpClicked) })
            OrSignUpWithText(modifier = Modifier.weight(0.1f))
            SocialMediaIconsRow(
                onSignInWithGoogleClicked = onSignInWithGoogleClicked,
                onSignInWithFacebookClicked = onSignInWithFacebookClicked,
                onSignInWithMicrosoftClicked = onSignInWithMicrosoftClicked
            )
            SignInTextButton(modifier = Modifier.weight(0.1f), onClick = navigateToSignIn)

        }
    }
}

@Preview(device = "spec:width=1080px,height=2000px,dpi=440")
@Composable
fun SignUpScreenContentPreview() {
    KhanaTheme {
        SignUpScreenContent(
            state = SignUpUiState(),
            action = {},
            navigateToSignIn = {},
            onSignInWithGoogleClicked = {},
        )

    }
}
package org.zaed.khana.presentation.Login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zaed.khana.presentation.Login.component.EmailTextField
import org.zaed.khana.presentation.Login.component.ForgetPasswordTextButton
import org.zaed.khana.presentation.Login.component.OrSignInWithText
import org.zaed.khana.presentation.Login.component.PasswordTextField
import org.zaed.khana.presentation.Login.component.SignInButton
import org.zaed.khana.presentation.Login.component.SignInTitle
import org.zaed.khana.presentation.Login.component.SignInWelcomeSentence
import org.zaed.khana.presentation.Login.component.SignUpTextButton
import org.zaed.khana.presentation.Login.component.SocialMediaIconsRow
import org.zaed.khana.ui.theme.KhanaTheme

@Composable
fun LoginScreen(
    state: LoginUiState = LoginUiState(),
    action: (LoginUIAction) -> Unit = {}
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
                onValueChanged = action,
            )
            PasswordTextField(
                modifier = Modifier.weight(0.1f),
                value = state.password,
                onValueChanged = action,
            )
            ForgetPasswordTextButton(modifier = Modifier.weight(0.1f), action)
            SignInButton(onClick = action)
            OrSignInWithText(modifier = Modifier.weight(0.1f))
            SocialMediaIconsRow(
                modifier = Modifier.weight(0.1f),
                action = action
            )
            SignUpTextButton(modifier = Modifier.weight(0.1f), action)
        }

    }
}


@Composable
@Preview(
    showBackground = true, showSystemUi = true,
    device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420"
)
fun LoginScreenPreview() {
    KhanaTheme {
        LoginScreen()
    }
}
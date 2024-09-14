package org.zaed.khana.presentation.Login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.presentation.Login.component.LoginScreenContent
import org.zaed.khana.presentation.Login.component.SignInWithFacebook
import org.zaed.khana.presentation.Login.component.googleSignInOption
import org.zaed.khana.presentation.Login.component.rememberFirebaseAuthLauncher
import org.zaed.khana.ui.theme.KhanaTheme


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val facebookLauncher = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)) {
    }
    SignInWithFacebook(
        loginManager = loginManager,
        callbackManager = callbackManager,
        result = { result ->
            viewModel.signInWithProvider(result)
        }
    )
    val launcher = rememberFirebaseAuthLauncher(
        result = { result ->
            viewModel.signInWithProvider(result)
        },
    )
    val googleSignInClient = googleSignInOption(context)
    LoginScreenContent(
        state = state,
        action = { viewModel.handleUiState(it) },
        //TODO
        navigateToForgetPassword = {},
        //TODO
        navigateToSignUp = {},
        //TODO
        onSignInWithGoogleClicked = {
            println("onSignInWithGoogleClicked")
            launcher.launch(googleSignInClient.signInIntent)
                                    },
        onSignInWithFacebookClicked = {
            println("onSignInWithFacebookClicked")
            facebookLauncher.launch(listOf("email", "public_profile"))
        }
    )

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
package org.zaed.khana.presentation.auth.signup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import org.koin.androidx.compose.koinViewModel
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.presentation.auth.component.SignInWithFacebook
import org.zaed.khana.presentation.auth.component.googleSignInOption
import org.zaed.khana.presentation.auth.component.rememberFirebaseAuthLauncher
import org.zaed.khana.presentation.theme.KhanaTheme

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = koinViewModel(),
    navigateToCompleteProfile :()-> Unit = {},
    navigateToSignIn :()-> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(state.userMessage) {
        if(state.userMessage == AuthResults.SIGNUP_SUCCESS)
            navigateToCompleteProfile()
    }
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
            viewModel.signUpWithProvider(result)
        }
    )
    val launcher = rememberFirebaseAuthLauncher(
        result = { result ->
            viewModel.signUpWithProvider(result)
        },
    )
    val googleSignInClient = googleSignInOption(context)
    SignUpScreenContent(
        state = state,
        action = { viewModel.handleUiState(it) },
        navigateToSignIn = navigateToSignIn,
        onSignInWithGoogleClicked = {
            println("onSignUpWithGoogleClicked")
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
        SignUpScreenContent(
            state = SignUpUiState(),
            action = {},
            navigateToSignIn = {},
            onSignInWithGoogleClicked = {},
        )
    }
}
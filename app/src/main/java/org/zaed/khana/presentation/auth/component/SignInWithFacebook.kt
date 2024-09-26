package org.zaed.khana.presentation.auth.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.Result

@Composable
fun SignInWithFacebook(
    loginManager: LoginManager,
    callbackManager: CallbackManager,
    result:(Result<AuthResult, AuthResults>)->Unit
) {
    val scope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                result(Result.failure(AuthResults.LOGIN_FAILED))
            }

            override fun onError(error: FacebookException) {
                result(Result.failure(AuthResults.LOGIN_FAILED))
            }

            override fun onSuccess(result: LoginResult) {
                scope.launch {
                    val token = result.accessToken.token
                    println("token: $token")
                    val credential = FacebookAuthProvider.getCredential(token)
                    val authResult = Firebase.auth.signInWithCredential(credential).await()
                    if (authResult.user != null) {
                        result(Result.Success(authResult))
                    } else {
                        result(Result.failure(AuthResults.LOGIN_FAILED))
                    }
                }
            }
        })
        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }
}
package org.zaed.khana.presentation.Login.component

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.AuthResult
import org.zaed.khana.R
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.Result



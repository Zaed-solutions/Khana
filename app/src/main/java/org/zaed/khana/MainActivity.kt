package org.zaed.khana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.zaed.khana.presentation.auth.login.LoginScreen
import org.zaed.khana.presentation.auth.signup.SignUpScreen
import org.zaed.khana.ui.theme.KhanaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KhanaTheme {
                SignUpScreen()
            }
        }
    }
}

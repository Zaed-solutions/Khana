package org.zaed.khana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.serialization.Serializable
import org.zaed.khana.presentation.App
import org.zaed.khana.presentation.auth.forgetPassword.ForgetPasswordScreen
import org.zaed.khana.presentation.auth.otp.OTPVerificationScreen
import org.zaed.khana.presentation.cart.CartScreen
import org.zaed.khana.presentation.category.CategoryScreen
import org.zaed.khana.presentation.coupons.CouponsScreen
import org.zaed.khana.presentation.home.HomeScreen
import org.zaed.khana.presentation.search.SearchScreen
import org.zaed.khana.presentation.theme.KhanaTheme
import org.zaed.khana.presentation.wishlist.WishlistScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KhanaTheme {
                App()
            }
        }
    }
}



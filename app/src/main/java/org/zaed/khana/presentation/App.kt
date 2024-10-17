package org.zaed.khana.presentation

import androidx.compose.compiler.plugins.kotlin.EmptyFunctionMetrics.composable
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.zaed.khana.app.navigation.ForgetPasswordScreen
import org.zaed.khana.app.navigation.HomeScreen
import org.zaed.khana.app.navigation.LoginScreen
import org.zaed.khana.app.navigation.NewPasswordScreen
import org.zaed.khana.app.navigation.OtpVerificationScreen
import org.zaed.khana.app.navigation.SignUpScreen
import org.zaed.khana.presentation.auth.forgetPassword.ForgetPasswordScreen
import org.zaed.khana.presentation.auth.login.LoginScreen
import org.zaed.khana.presentation.auth.newpassword.NewPasswordScreen
import org.zaed.khana.presentation.auth.otp.OTPVerificationScreen
import org.zaed.khana.presentation.auth.signup.SignUpScreen
import org.zaed.khana.presentation.home.HomeScreen


@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LoginScreen) {
        composable<ForgetPasswordScreen> {
            ForgetPasswordScreen(
                navigateToOtpScreen = { navController.navigate(OtpVerificationScreen(it)) }
            )
        }
        composable<OtpVerificationScreen> { backStackEntry ->
            val params = backStackEntry.toRoute<OtpVerificationScreen>()
            OTPVerificationScreen(
                email = params.email,
                navigateToNewPasswordScreen = { navController.navigate(NewPasswordScreen) }
            )
        }
        composable<LoginScreen> {
            LoginScreen(
                navigateToForgetPasswordScreen = { navController.navigate(ForgetPasswordScreen) },
                navigateToHomeScreen = {
                    navController.navigate(HomeScreen)
                },
                navigateToSignUpScreen = { navController.navigate(SignUpScreen) }
            )
        }
        composable<SignUpScreen> {
            SignUpScreen(
                navigateToHomeScreen = {
                    navController.navigate(HomeScreen)
                },
                navigateToSignIn = { navController.navigate(LoginScreen) }
            )
        }
        composable<NewPasswordScreen> {
            NewPasswordScreen()
        }
        composable<HomeScreen> {
            HomeScreen()
        }

    }
}



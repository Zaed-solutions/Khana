package org.zaed.khana.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.zaed.khana.app.navigation.CartScreen
import org.zaed.khana.app.navigation.CategoryScreen
import org.zaed.khana.app.navigation.CheckoutScreen
import org.zaed.khana.app.navigation.CouponsScreen
import org.zaed.khana.app.navigation.CustomNavType
import org.zaed.khana.app.navigation.FilterScreen
import org.zaed.khana.app.navigation.ForgetPasswordScreen
import org.zaed.khana.app.navigation.HelpCenterScreen
import org.zaed.khana.app.navigation.HomeScreen
import org.zaed.khana.app.navigation.LeaveReviewScreen
import org.zaed.khana.app.navigation.LoginScreen
import org.zaed.khana.app.navigation.MyOrdersScreen
import org.zaed.khana.app.navigation.NewPasswordScreen
import org.zaed.khana.app.navigation.OtpVerificationScreen
import org.zaed.khana.app.navigation.ProductDetailsScreen
import org.zaed.khana.app.navigation.SearchScreen
import org.zaed.khana.app.navigation.SignUpScreen
import org.zaed.khana.app.navigation.TrackOrderScreen
import org.zaed.khana.app.navigation.WishlistScreen
import org.zaed.khana.data.model.ProductFilter
import org.zaed.khana.presentation.auth.forgetPassword.ForgetPasswordScreen
import org.zaed.khana.presentation.auth.login.LoginScreen
import org.zaed.khana.presentation.auth.newpassword.NewPasswordScreen
import org.zaed.khana.presentation.auth.otp.OTPVerificationScreen
import org.zaed.khana.presentation.auth.signup.SignUpScreen
import org.zaed.khana.presentation.cart.CartScreen
import org.zaed.khana.presentation.category.CategoryScreen
import org.zaed.khana.presentation.checkout.CheckoutScreen
import org.zaed.khana.presentation.coupons.CouponsScreen
import org.zaed.khana.presentation.filter.FilterScreen
import org.zaed.khana.presentation.helpcenter.HelpCenterScreen
import org.zaed.khana.presentation.home.HomeScreen
import org.zaed.khana.presentation.leavereview.LeaveReviewScreen
import org.zaed.khana.presentation.myorders.MyOrdersScreen
import org.zaed.khana.presentation.productdetails.ProductDetailsScreen
import org.zaed.khana.presentation.search.SearchScreen
import org.zaed.khana.presentation.trackorder.TrackOrderScreen
import kotlin.reflect.typeOf


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
                    navController.navigate(HomeScreen())
                },
                navigateToSignIn = { navController.navigate(LoginScreen) }
            )
        }
        composable<NewPasswordScreen> {
            NewPasswordScreen()
        }
        composable<HomeScreen>(
            typeMap = mapOf(
                typeOf<ProductFilter>() to CustomNavType.ProductFilterType
            )
        ) { backStackEntry ->
            val filter = backStackEntry.toRoute<HomeScreen>().filter
            HomeScreen(
                productFilter = filter,
                onNavigateToNotificationsScreen = {
                    //TODO
                },
                onNavigateToFilterScreen = {
                    navController.navigate(FilterScreen)
                },
                onNavigateToSearchScreen = {
                    navController.navigate(SearchScreen)
                },
                onNavigateToProductDetailsScreen = { productId ->
                    navController.navigate(ProductDetailsScreen(productId = productId))
                }
            )
        }
        composable<CartScreen> {
            CartScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onNavigateToCheckout = {
                    navController.navigate(CheckoutScreen)
                }
            )
        }
        composable<CategoryScreen> { backStackEntry ->
            val category = backStackEntry.toRoute<CategoryScreen>().category
            CategoryScreen(
                category = category,
                onBackPressed = {
                    navController.popBackStack()
                },
                onNavigateToProductDetails = { productId ->
                    navController.navigate(ProductDetailsScreen(productId = productId))
                }
            )
        }
        composable<CheckoutScreen> {
            CheckoutScreen(
                onBackPressed = { navController.popBackStack() },
                onNavigateToPaymentScreen = {
                    /*TODO*/
                }
            )
        }
        composable<CouponsScreen> {
            CouponsScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
        composable<FilterScreen> {
            FilterScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onNavigateToHome = { filter ->
                    navController.navigate(HomeScreen(filter = filter))
                }
            )
        }
        composable<HelpCenterScreen> {
            HelpCenterScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable<LeaveReviewScreen> { backStackEntry ->
            val params = backStackEntry.toRoute<LeaveReviewScreen>()
            LeaveReviewScreen(
                orderId = params.orderId,
                cartItemId = params.cartItemId,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable<MyOrdersScreen> {
            MyOrdersScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onNavigateToLeaveReview = { orderId, cartItemId ->
                    navController.navigate(
                        LeaveReviewScreen(
                            orderId = orderId,
                            cartItemId = cartItemId
                        )
                    )
                },
                onNavigateToTrackOrder = { orderId, cartItemId ->
                    navController.navigate(
                        TrackOrderScreen(
                            orderId = orderId,
                            cartItemId = cartItemId
                        )
                    )
                }
            )
        }
        composable<ProductDetailsScreen> { backStackEntry ->
            val productId = backStackEntry.toRoute<ProductDetailsScreen>().productId
            ProductDetailsScreen(
                productId = productId,
                onBackPressed = {
                    navController.popBackStack()
                },
            )
        }
        composable<SearchScreen> {
            SearchScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onNavigateToProductDetails = { productId ->
                    navController.navigate(ProductDetailsScreen(productId = productId))
                }
            )
        }
        composable<TrackOrderScreen> { backStackEntry ->
            val params = backStackEntry.toRoute<TrackOrderScreen>()
            TrackOrderScreen(
                orderId = params.orderId,
                cartItemId = params.cartItemId,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable<WishlistScreen> {
            org.zaed.khana.presentation.wishlist.WishlistScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onNavigateToProductDetails = { productId ->
                    navController.navigate(ProductDetailsScreen(productId = productId))
                }
            )
        }
    }
}



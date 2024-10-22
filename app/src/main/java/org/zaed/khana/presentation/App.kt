package org.zaed.khana.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.zaed.khana.app.navigation.BottomNavigation
import org.zaed.khana.app.navigation.BottomNavigationBar
import org.zaed.khana.app.navigation.CustomNavType
import org.zaed.khana.app.navigation.Destinations.CartScreen
import org.zaed.khana.app.navigation.Destinations.CategoryScreen
import org.zaed.khana.app.navigation.Destinations.CheckoutScreen
import org.zaed.khana.app.navigation.Destinations.CompleteProfileScreen
import org.zaed.khana.app.navigation.Destinations.CouponsScreen
import org.zaed.khana.app.navigation.Destinations.FilterScreen
import org.zaed.khana.app.navigation.Destinations.ForgetPasswordScreen
import org.zaed.khana.app.navigation.Destinations.HelpCenterScreen
import org.zaed.khana.app.navigation.Destinations.HomeScreen
import org.zaed.khana.app.navigation.Destinations.LeaveReviewScreen
import org.zaed.khana.app.navigation.Destinations.LoginScreen
import org.zaed.khana.app.navigation.Destinations.MyOrdersScreen
import org.zaed.khana.app.navigation.Destinations.NewPasswordScreen
import org.zaed.khana.app.navigation.Destinations.OtpVerificationScreen
import org.zaed.khana.app.navigation.Destinations.PasswordManagerScreen
import org.zaed.khana.app.navigation.Destinations.PaymentScreen
import org.zaed.khana.app.navigation.Destinations.PrivacyPolicyScreen
import org.zaed.khana.app.navigation.Destinations.ProductDetailsScreen
import org.zaed.khana.app.navigation.Destinations.ProfileScreen
import org.zaed.khana.app.navigation.Destinations.SearchScreen
import org.zaed.khana.app.navigation.Destinations.SettingsScreen
import org.zaed.khana.app.navigation.Destinations.SignUpScreen
import org.zaed.khana.app.navigation.Destinations.TrackOrderScreen
import org.zaed.khana.app.navigation.Destinations.WishlistScreen
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
import org.zaed.khana.presentation.passwordmanager.PasswordManagerScreen
import org.zaed.khana.presentation.payment.PaymentScreen
import org.zaed.khana.presentation.privacy.PrivacyPolicyScreen
import org.zaed.khana.presentation.productdetails.ProductDetailsScreen
import org.zaed.khana.presentation.search.SearchScreen
import org.zaed.khana.presentation.settings.SettingsScreen
import org.zaed.khana.presentation.trackorder.TrackOrderScreen
import kotlin.reflect.typeOf


@Composable
fun App() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = (navBackStackEntry?.destination?.route
        ?: HomeScreen::class.qualifiedName.orEmpty()).substringBefore("?")
    val bottomNavRoutes = BottomNavigation.entries.map{ it.route::class.qualifiedName.orEmpty() }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
        ,
        bottomBar = {
            AnimatedVisibility(
                visible = bottomNavRoutes.contains(currentRoute),
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route)
                    }
                )

            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = LoginScreen
        ) {
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
                        navController.navigate(HomeScreen()) {
                            popUpTo(LoginScreen) { inclusive = true }
                        }
                    },
                    navigateToSignUpScreen = { navController.navigate(SignUpScreen) }
                )
            }
            composable<SignUpScreen> {
                SignUpScreen(
                    navigateToCompleteProfile = {
                        navController.navigate(CompleteProfileScreen) {
                            popUpTo(SignUpScreen) { inclusive = true }
                        }
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
                    onNavigateToFilterScreen = { initialFilter ->
                        navController.navigate(FilterScreen(initialFilter))
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
                    onNavigateToPaymentScreen = { orderId ->
                        navController.navigate(PaymentScreen(orderId = orderId))
                    }
                )
            }
            composable<CouponsScreen> {
                CouponsScreen(
                    onBackPressed = { navController.popBackStack() }
                )
            }
            composable<FilterScreen>(
                typeMap = mapOf(
                    typeOf<ProductFilter>() to CustomNavType.ProductFilterType
                )
            ) { backStackEntry ->
                val initialFilter = backStackEntry.toRoute<FilterScreen>().initialFilter
                FilterScreen(
                    initialFilter = initialFilter,
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
            composable<PrivacyPolicyScreen> {
                PrivacyPolicyScreen(
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }
            composable<SettingsScreen> {
                SettingsScreen(
                    onBackPressed = { navController.popBackStack() },
                    onNavigateToPasswordManager = { navController.navigate(PasswordManagerScreen) },
                    onNavigateToNotificationSettings = { /*TODO*/ },
                    onNavigateToLogin = { navController.navigate(LoginScreen) }
                )
            }
            composable<PasswordManagerScreen> {
                PasswordManagerScreen(
                    onBackPressed = { navController.popBackStack() }
                )
            }
            composable<PaymentScreen> { backStackEntry ->
                val orderId = backStackEntry.toRoute<PaymentScreen>().orderId
                PaymentScreen(
                    orderId = orderId,
                    onBackPressed = {
                        navController.popBackStack()
                    },
                    onNavigateToHome = {
                        navController.navigate(HomeScreen())
                    }
                )
            }
            composable<CompleteProfileScreen> {
                org.zaed.khana.presentation.auth.completeprofile.CompleteProfileScreen(
                    navigateToHomeScreen = {
                        navController.navigate(HomeScreen()) {
                            popUpTo(CompleteProfileScreen) { inclusive = true }
                        }
                    }
                )
            }
            composable<ProfileScreen> {
                org.zaed.khana.presentation.profile.ProfileScreen(
                    onBackPressed = { navController.popBackStack() },
                    onNavigateToHelpCenter = { navController.navigate(HelpCenterScreen) },
                    onNavigateToMyOrders = { navController.navigate(MyOrdersScreen) },
                    onNavigateToPaymentMethods = { /*TODO*/ },
                    onNavigateToPrivacyPolicy = { navController.navigate(PrivacyPolicyScreen) },
                    onNavigateToSettings = { navController.navigate(SettingsScreen) },
                    onNavigateToLogin = { navController.navigate(LoginScreen) })
            }
        }
    }
}



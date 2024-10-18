package org.zaed.khana.app.navigation

import kotlinx.serialization.Serializable
import org.zaed.khana.data.model.ProductFilter

@Serializable
data object ForgetPasswordScreen

@Serializable
data class OtpVerificationScreen(val email: String)

@Serializable
data object LoginScreen

@Serializable
data object SignUpScreen

@Serializable
data object NewPasswordScreen

@Serializable
data class HomeScreen(val filter: ProductFilter = ProductFilter())

@Serializable
data object CartScreen

@Serializable
data class CategoryScreen(val category: String)

@Serializable
data object CheckoutScreen

@Serializable
data object CouponsScreen

@Serializable
data object FilterScreen

@Serializable
data object HelpCenterScreen

@Serializable
data class LeaveReviewScreen(val orderId: String, val cartItemId: String)

@Serializable
data object MyOrdersScreen

@Serializable
data class ProductDetailsScreen(val productId: String)

@Serializable
data object SearchScreen

@Serializable
data class TrackOrderScreen(val orderId: String, val cartItemId: String)

@Serializable
data object WishlistScreen

@Serializable
data object PrivacyPolicyScreen
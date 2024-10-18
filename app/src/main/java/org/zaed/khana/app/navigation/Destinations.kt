package org.zaed.khana.app.navigation

import kotlinx.serialization.Serializable
import org.zaed.khana.data.model.ProductFilter

@Serializable
object ForgetPasswordScreen

@Serializable
data class OtpVerificationScreen(val email: String)

@Serializable
object LoginScreen

@Serializable
object SignUpScreen

@Serializable
object NewPasswordScreen

@Serializable
data class HomeScreen(val filter: ProductFilter = ProductFilter())

@Serializable
object CartScreen

@Serializable
data class CategoryScreen(val category: String)

@Serializable
object CheckoutScreen

@Serializable
object CouponsScreen

@Serializable
object FilterScreen

@Serializable
object HelpCenterScreen

@Serializable
data class LeaveReviewScreen(val orderId: String, val cartItemId: String)

@Serializable
object MyOrdersScreen

@Serializable
data class ProductDetailsScreen(val productId: String)

@Serializable
object SearchScreen

@Serializable
data class TrackOrderScreen(val orderId: String, val cartItemId: String)

@Serializable
object WishlistScreen
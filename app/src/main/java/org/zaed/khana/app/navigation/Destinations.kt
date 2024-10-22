package org.zaed.khana.app.navigation

import kotlinx.serialization.Serializable
import org.zaed.khana.data.model.ProductFilter



sealed interface Destinations{
    @Serializable
    data object ForgetPasswordScreen: Destinations

    @Serializable
    data class OtpVerificationScreen(val email: String): Destinations

    @Serializable
    data object LoginScreen: Destinations

    @Serializable
    data object SignUpScreen: Destinations

    @Serializable
    data object NewPasswordScreen: Destinations
    @Serializable
    data class HomeScreen(val filter: ProductFilter = ProductFilter()): Destinations

    @Serializable
    data object CartScreen: Destinations

    @Serializable
    data class CategoryScreen(val category: String): Destinations

    @Serializable
    data object CheckoutScreen: Destinations

    @Serializable
    data object CouponsScreen: Destinations

    @Serializable
    data class FilterScreen(val initialFilter: ProductFilter): Destinations

    @Serializable
    data object HelpCenterScreen: Destinations

    @Serializable
    data class LeaveReviewScreen(val orderId: String, val cartItemId: String): Destinations

    @Serializable
    data object MyOrdersScreen: Destinations

    @Serializable
    data class ProductDetailsScreen(val productId: String): Destinations

    @Serializable
    data object SearchScreen: Destinations

    @Serializable
    data class TrackOrderScreen(val orderId: String, val cartItemId: String): Destinations

    @Serializable
    data object WishlistScreen: Destinations

    @Serializable
    data object PrivacyPolicyScreen: Destinations

    @Serializable
    data object SettingsScreen: Destinations

    @Serializable
    data object PasswordManagerScreen: Destinations

    @Serializable
    data class PaymentScreen(val orderId: String): Destinations

    @Serializable
    data object ProfileScreen: Destinations

    @Serializable
    data object CompleteProfileScreen: Destinations
}

package org.zaed.khana.data.util

import kotlinx.serialization.Serializable


@Serializable
sealed interface Error

fun Error.userMessage(): String {
    return when (this) {
        is AuthResults -> this.userMessage
        is EmailFieldError -> this.userMessage
        is PasswordFieldError -> this.userMessage
        is NameFieldError -> this.userMessage
        is TermsAndConditionsError -> this.userMessage
        is AdvertisementResult -> userMessage
        is CategoryResult -> userMessage
        is ProductResult -> userMessage
        is CartResult -> userMessage
    }
}

fun Error.isNotIdle(): Boolean {
    return when (this) {
        is AdvertisementResult -> this != AdvertisementResult.IDLE
        is CategoryResult -> this != CategoryResult.IDLE
        is ProductResult -> this != ProductResult.IDLE
        is AuthResults -> return this != AuthResults.IDLE
        is EmailFieldError -> return this != EmailFieldError.IDLE
        is PasswordFieldError -> return this != PasswordFieldError.IDLE
        is NameFieldError -> return this != NameFieldError.IDLE
        is TermsAndConditionsError -> return this != TermsAndConditionsError.IDLE
        is CartResult -> this != CartResult.IDLE
    }
}

@Serializable
enum class EmailFieldError(val userMessage: String):Error {
    IDLE(""),
    INVALID_EMAIL("Invalid Email"),
    EMAIL_FIELD_IS_EMPTY("Email field is empty"),
    EMAIL_NOT_REGISTERED("Email not registered"),
    EMAIL_ALREADY_REGISTERED("Email already registered"),
    RESET_EMAIL_SENT("Reset Email Sent"),
}
@Serializable
enum class AdvertisementResult(val userMessage: String) : Error {
    IDLE(""),
    SERVER_ERROR("Failed to fetch advertisements"),
    NETWORK_ERROR("Failed to connect to the network"),
}

@Serializable
enum class NameFieldError(val userMessage: String):Error{
    IDLE(""),
    INVALID_NAME("Invalid Name"),
    NAME_FIELD_IS_EMPTY("Name field is empty"),
}
@Serializable
enum class TermsAndConditionsError(val userMessage: String):Error{
    IDLE(""),
    TERMS_AND_CONDITIONS_NOT_ACCEPTED("Terms and conditions not accepted"),
}
@Serializable
enum class PasswordFieldError(val userMessage: String):Error {
    IDLE(""),
    INVALID_PASSWORD("Invalid Password"),
    PASSWORD_FIELD_IS_EMPTY("Password field is empty"),
    PASSWORD_DOES_NOT_MATCH("Password does not match"),
}
@Serializable
enum class CategoryResult(val userMessage: String) : Error {
    IDLE(""),
    SERVER_ERROR("Failed to fetch categories"),
    NETWORK_ERROR("Failed to connect to the network"),
}

@Serializable
enum class ProductResult(val userMessage: String) : Error {
    IDLE(""),
    FETCH_LABELS_FAILED("Failed to fetch product labels"),
    CHECK_IF_PRODUCT_IS_WISHLISTED_FAILED("Failed to check whether product is wishlisted"),
    FETCH_FLASH_SALE_END_TIME_FAILED("Failed to fetch flash sale end time"),
    FETCH_PRODUCTS_FAILED("Failed to fetch products"),
    FETCH_WISHLISTED_PRODUCTS_FAILED("Failed to fetch wishlisted products"),
    ADD_ITEM_TO_CART_FAILED("Failed to add the item to the cart"),
    ADD_WISHLISTED_PRODUCTS_FAILED("Failed to add wishlisted product"),
    REMOVE_WISHLISTED_PRODUCTS_FAILED("Failed to remove wishlisted product"),
    NETWORK_ERROR("Failed to connect to the network"),
    SERVER_ERROR("Server error"),
}
@Serializable
enum class CartResult(val userMessage: String) : Error {
    IDLE(""),
    FETCH_CART_ITEMS_FAILED("Failed to fetch cart items"),
    FETCH_DELIVERY_FEE_FAILED("Failed to fetch delivery fee"),
    ADD_ITEM_TO_CART_FAILED("Failed to add the item to the cart"),
    REMOVE_ITEM_FROM_CART_FAILED("Failed to remove the item from the cart"),
    UPDATE_ITEM_QUANTITY_FAILED("Failed to update the item quantity"),
    APPLY_PROMO_CODE_FAILED("Failed to apply promo code"),
    NETWORK_ERROR("Failed to connect to the network"),
    SERVER_ERROR("Server error"),
}

@Serializable
enum class AuthResults(val userMessage: String) : Error {
    IDLE(""),
    INVALID_NAME("Invalid Name"),
    PASSWORD_DOES_NOT_MATCH("Password does not match"),
    INVALID_EMAIL("Invalid Email"),
    INVALID_PASSWORD("Invalid Password"),
    RESET_EMAIL_SENT("Reset Email Sent"),
    LOGIN_SUCCESS("Login Successful"),
    LOGIN_FIRST_TO_ACCESS_ALL_FEATURES("Login first to access all features"),
    LOGIN_FAILED("Login Failed"),
    SIGNUP_SUCCESS("Signup Successful"),
    SIGNUP_FAILED("Signup Failed"),
    LOGOUT_SUCCESS("Logout Successful"),
    LOGOUT_FAILED("Logout Failed"),
    SERVER_ERROR("Server error"),
    NETWORK_ERROR("Network error"),
    USER_NOT_FOUND("User not found"),
}
package org.zaed.khana.data.util

import kotlinx.serialization.Serializable


@kotlinx.serialization.Serializable
sealed interface Error

fun Error.userMessage(): String {
    return when (this) {
        is AuthResults -> userMessage
        is AdvertisementResult -> userMessage
        is CategoryResult -> userMessage
        is ProductResult -> userMessage
    }
}

fun Error.isNotIdle(): Boolean {
    return when (this) {
        is AuthResults -> this !in listOf(
            AuthResults.IDLE,
            AuthResults.INVALID_EMAIL,
            AuthResults.INVALID_NAME,
            AuthResults.INVALID_PASSWORD,
            AuthResults.PASSWORD_DOES_NOT_MATCH
        )
        is AdvertisementResult -> this != AdvertisementResult.IDLE
        is CategoryResult -> this != CategoryResult.IDLE
        is ProductResult -> this != ProductResult.IDLE
    }
}

@Serializable
enum class AdvertisementResult(val userMessage: String) : Error {
    IDLE(""),
    SERVER_ERROR("Failed to fetch advertisements"),
    NETWORK_ERROR("Failed to connect to the network"),
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
    ADD_WISHLISTED_PRODUCTS_FAILED("Failed to add wishlisted product"),
    REMOVE_WISHLISTED_PRODUCTS_FAILED("Failed to remove wishlisted product"),
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

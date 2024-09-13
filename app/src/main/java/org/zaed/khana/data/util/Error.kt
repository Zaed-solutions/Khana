package org.zaed.khana.data.util

import kotlinx.serialization.Serializable


@kotlinx.serialization.Serializable
sealed interface Error

fun Error.userMessage(): String {
    return when (this) {
        is AuthResults -> userMessage
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
    }
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
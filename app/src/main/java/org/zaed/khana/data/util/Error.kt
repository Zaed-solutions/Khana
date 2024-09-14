package org.zaed.khana.data.util

import kotlinx.serialization.Serializable


@Serializable
sealed interface Error

fun Error.userMessage(): String {
    return when (this) {
        is AuthResults -> this.userMessage
        is EmailFieldError -> this.userMessage
        is PasswordFieldError -> this.userMessage
    }
}

fun Error.isNotIdle(): Boolean {
    when(this){
        is AuthResults -> return this != AuthResults.IDLE
        is EmailFieldError -> return this != EmailFieldError.IDLE
        is PasswordFieldError -> return this != PasswordFieldError.IDLE
    }
}

@Serializable
enum class EmailFieldError(val userMessage: String):Error{
    IDLE(""),
    INVALID_EMAIL("Invalid Email"),
    EMAIL_FIELD_IS_EMPTY("Email field is empty"),
    EMAIL_NOT_REGISTERED("Email not registered"),
    EMAIL_ALREADY_REGISTERED("Email already registered"),
    RESET_EMAIL_SENT("Reset Email Sent"),

}
@Serializable
enum class PasswordFieldError(val userMessage: String):Error {
    IDLE(""),
    INVALID_PASSWORD("Invalid Password"),
    PASSWORD_FIELD_IS_EMPTY("Password field is empty"),
    PASSWORD_DOES_NOT_MATCH("Password does not match"),
}
@Serializable
enum class AuthResults(val userMessage: String) : Error {
    IDLE(""),
    INVALID_NAME("Invalid Name"),
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
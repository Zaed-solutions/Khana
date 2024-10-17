package org.zaed.khana.presentation.auth.otp

import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.PasswordFieldError


data class OtpUiState(
    val email: String = "mozarea0@gmail.com",
    val otp1: String = "",
    val otp2: String = "",
    val otp3: String = "",
    val otp4: String = "",
    val otpError1: PasswordFieldError = PasswordFieldError.IDLE,
    val otpError2: PasswordFieldError = PasswordFieldError.IDLE,
    val otpError3: PasswordFieldError = PasswordFieldError.IDLE,
    val otpError4: PasswordFieldError = PasswordFieldError.IDLE,
    val userMessage : AuthResults = AuthResults.IDLE,
    val loading: Boolean = false
)

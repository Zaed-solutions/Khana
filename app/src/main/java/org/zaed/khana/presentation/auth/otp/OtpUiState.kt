package org.zaed.khana.presentation.auth.otp

import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.PasswordFieldError


data class OtpUiState(
    val otp: String = "",
    val otpError: PasswordFieldError = PasswordFieldError.IDLE,
    val userMessage : AuthResults = AuthResults.IDLE,
    val loading: Boolean = false
)

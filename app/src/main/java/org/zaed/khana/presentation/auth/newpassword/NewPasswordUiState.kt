package org.zaed.khana.presentation.auth.newpassword

import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.EmailFieldError
import org.zaed.khana.data.util.PasswordFieldError


data class NewPasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    val passwordError: PasswordFieldError = PasswordFieldError.IDLE,
    val confirmPasswordError: PasswordFieldError = PasswordFieldError.IDLE,
    val userMessage : AuthResults = AuthResults.IDLE,
    val loading: Boolean = false
)

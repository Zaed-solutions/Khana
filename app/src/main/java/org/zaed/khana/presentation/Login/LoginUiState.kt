package org.zaed.khana.presentation.Login

import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.EmailFieldError
import org.zaed.khana.data.util.PasswordFieldError


data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: EmailFieldError = EmailFieldError.IDLE,
    val passwordError: PasswordFieldError = PasswordFieldError.IDLE,
    val userMessage : AuthResults = AuthResults.IDLE,
    val loading: Boolean = false
)

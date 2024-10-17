package org.zaed.khana.presentation.auth.forgetPassword

import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.EmailFieldError
import org.zaed.khana.data.util.ForgetPasswordResult
import org.zaed.khana.data.util.PasswordFieldError


data class ForgetPasswordUiState(
    val email: String = "",
    val emailError: EmailFieldError = EmailFieldError.IDLE,
    val userMessage : ForgetPasswordResult = ForgetPasswordResult.IDLE,
    val loading: Boolean = false
)

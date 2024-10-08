package org.zaed.khana.presentation.auth.signup

import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.EmailFieldError
import org.zaed.khana.data.util.NameFieldError
import org.zaed.khana.data.util.PasswordFieldError
import org.zaed.khana.data.util.TermsAndConditionsError

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val termsAndConditions: Boolean = false,
    val nameError: NameFieldError = NameFieldError.IDLE,
    val termsAndConditionsError : TermsAndConditionsError = TermsAndConditionsError.IDLE,
    val emailError: EmailFieldError = EmailFieldError.IDLE,
    val passwordError: PasswordFieldError = PasswordFieldError.IDLE,
    val userMessage : AuthResults = AuthResults.IDLE,
    val loading: Boolean = false
)
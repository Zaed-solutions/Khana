package org.zaed.khana.presentation.Login

import org.zaed.khana.presentation.utils.AuthError

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: AuthError = AuthError.NO_ERROR,
    val passwordError: AuthError = AuthError.NO_ERROR,
    val loading: Boolean = false
)

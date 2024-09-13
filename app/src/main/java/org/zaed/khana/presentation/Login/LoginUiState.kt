package org.zaed.khana.presentation.Login

import org.zaed.khana.data.util.AuthResults


data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: AuthResults = AuthResults.IDLE,
    val passwordError: AuthResults = AuthResults.IDLE,
    val loading: Boolean = false
)

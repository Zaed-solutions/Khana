package org.zaed.khana.presentation.passwordmanager

import org.zaed.khana.data.model.User

data class PasswordManagerUiState(
    val currentUser: User = User(),
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isCurrentPasswordWrong: Boolean = false,
    val isPasswordChanged: Boolean = false,
    val isNotMatchingPasswords: Boolean = false
)

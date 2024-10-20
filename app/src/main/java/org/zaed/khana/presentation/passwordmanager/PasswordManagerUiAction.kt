package org.zaed.khana.presentation.passwordmanager

sealed interface PasswordManagerUiAction {
    data object OnBackPressed: PasswordManagerUiAction
    data class OnCurrentPasswordChanged(val currentPassword: String): PasswordManagerUiAction
    data class OnNewPasswordChanged(val newPassword: String): PasswordManagerUiAction
    data class OnConfirmPasswordChanged(val confirmPassword: String): PasswordManagerUiAction
    data object OnChangePasswordClicked: PasswordManagerUiAction
}
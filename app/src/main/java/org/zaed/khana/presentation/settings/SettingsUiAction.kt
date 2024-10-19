package org.zaed.khana.presentation.settings

sealed interface SettingsUiAction{
    data object OnBackPressed: SettingsUiAction
    data object OnNotificationsSettingClicked: SettingsUiAction
    data object OnPasswordManagerClicked: SettingsUiAction
    data object OnDeleteAccountClicked: SettingsUiAction
    data object OnConfirmDeleteAccountClicked: SettingsUiAction
    data object OnCancelDeleteAccountClicked: SettingsUiAction
    data class OnVerifyPasswordAndDeleteAccount(val password: String): SettingsUiAction
}
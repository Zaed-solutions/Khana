package org.zaed.khana.presentation.settings.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import org.zaed.khana.R
import org.zaed.khana.presentation.settings.SettingsUiAction

enum class SettingsOptions(
    @StringRes val displayNameId: Int,
    val displayIcon: ImageVector,
    val action: SettingsUiAction
) {
    NOTIFICATIONS(
        R.string.notification_settings,
        Icons.Default.Notifications,
        SettingsUiAction.OnNotificationsSettingClicked
    ),
    PASSWORD_MANAGER(
        R.string.password_manager,
        Icons.Default.Key,
        SettingsUiAction.OnPasswordManagerClicked
    ),
    DELETE_ACCOUNT(
        R.string.delete_account,
        Icons.Default.Delete,
        SettingsUiAction.OnDeleteAccountClicked
    ),
}
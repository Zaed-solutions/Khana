package org.zaed.khana.presentation.settings

import org.zaed.khana.data.model.User

data class SettingsUiState(
    val currentUser: User = User(),
    val isWrongPassword: Boolean = false,
    val isAccountDeleted: Boolean = false
)
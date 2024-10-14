package org.zaed.khana.presentation.profile

import org.zaed.khana.data.model.User

data class ProfileUiState(
    val currentUser: User = User(),
    val isLoggedOut: Boolean = false
)

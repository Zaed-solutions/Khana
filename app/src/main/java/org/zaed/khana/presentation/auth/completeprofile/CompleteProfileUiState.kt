package org.zaed.khana.presentation.auth.completeprofile

import org.zaed.khana.data.model.User

data class CompleteProfileUiState(
    val currentUser: User = User(),
    val name: String = "",
    val phoneNumber: String = "",
    val imageUri: String? = null,
    val isProfileCompleted: Boolean = false,
    val loading: Boolean = false
)

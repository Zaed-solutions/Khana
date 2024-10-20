package org.zaed.khana.presentation.auth.userprofile

data class UserProfileUiState(
    val name: String = "",
    val phoneNumber: String = "",
    val imageUri: String? = null,
    val isProfileCompleted: Boolean = false,
    val loading: Boolean = false
)

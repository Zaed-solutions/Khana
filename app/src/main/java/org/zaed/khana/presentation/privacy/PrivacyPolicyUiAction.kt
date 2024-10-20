package org.zaed.khana.presentation.privacy

sealed interface PrivacyPolicyUiAction {
    data object OnBackPressed: PrivacyPolicyUiAction
}
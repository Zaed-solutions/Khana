package org.zaed.khana.presentation.profile

import android.net.Uri

sealed interface ProfileUiAction {
    data object OnBackPressed : ProfileUiAction
    data class OnAvatarPicked(val uri: Uri): ProfileUiAction
    data object OnPaymentMethodsClicked: ProfileUiAction
    data object OnMyOrdersClicked: ProfileUiAction
    data object OnSettingsClicked: ProfileUiAction
    data object OnHelpCenterClicked: ProfileUiAction
    data object OnPrivacyPolicyClicked: ProfileUiAction
    data object OnLogoutClicked: ProfileUiAction
}
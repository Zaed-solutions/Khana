package org.zaed.khana.presentation.profile

sealed interface ProfileUiAction {
    data object OnBackPressed : ProfileUiAction
    data object OnChangeAvatarClicked: ProfileUiAction
    data class OnUpdateAvatar(val byteArray: ByteArray): ProfileUiAction
    data object OnPaymentMethodsClicked: ProfileUiAction
    data object OnMyOrdersClicked: ProfileUiAction
    data object OnSettingsClicked: ProfileUiAction
    data object OnHelpCenterClicked: ProfileUiAction
    data object OnPrivacyPolicyClicked: ProfileUiAction
    data object OnLogoutClicked: ProfileUiAction
}
package org.zaed.khana.presentation.helpcenter

sealed interface HelpCenterUiAction {
    data object OnBackPressed : HelpCenterUiAction
}
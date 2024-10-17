package org.zaed.khana.presentation.trackorder

sealed interface TrackOrderUiAction {
    data object OnBackPressed: TrackOrderUiAction
}
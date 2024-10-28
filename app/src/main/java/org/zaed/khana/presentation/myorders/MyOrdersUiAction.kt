package org.zaed.khana.presentation.myorders

sealed interface MyOrdersUiAction {
    data object OnBackPressed: MyOrdersUiAction
    data class OnChangeTab(val tab: OrdersTabs): MyOrdersUiAction
    data class OnTrackItemClicked(val orderId: String, val productId: String): MyOrdersUiAction
    data class OnLeaveItemReviewClicked(val orderId: String, val productId: String): MyOrdersUiAction
}
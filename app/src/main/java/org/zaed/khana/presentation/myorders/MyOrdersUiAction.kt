package org.zaed.khana.presentation.myorders

sealed interface MyOrdersUiAction {
    data object OnBackPressed: MyOrdersUiAction
    data class OnChangeTab(val tab: OrdersTabs): MyOrdersUiAction
    data class OnReorderItemClicked(val itemId: String): MyOrdersUiAction
    data class OnTrackItemClicked(val itemId: String): MyOrdersUiAction
    data class OnLeaveItemReviewClicked(val orderId: String): MyOrdersUiAction
}
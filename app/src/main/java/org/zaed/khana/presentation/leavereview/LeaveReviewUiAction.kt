package org.zaed.khana.presentation.leavereview

sealed interface LeaveReviewUiAction {
    data object OnBackPressed: LeaveReviewUiAction
    data class OnRatingChanged(val rating: Int): LeaveReviewUiAction
    data class OnReviewChanged(val review: String): LeaveReviewUiAction
    data object OnReorderClicked: LeaveReviewUiAction
    data object OnSubmitClicked: LeaveReviewUiAction
    data object OnCancelClicked: LeaveReviewUiAction
}
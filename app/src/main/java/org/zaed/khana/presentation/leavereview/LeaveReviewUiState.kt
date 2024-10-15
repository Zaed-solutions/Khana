package org.zaed.khana.presentation.leavereview

import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.User

data class LeaveReviewUiState(
    val currentUser: User = User(),
    val item: CartItem = CartItem(),
    val rating: Int = 0,
    val review: String = "",
    val isReviewSubmitted: Boolean = false
)

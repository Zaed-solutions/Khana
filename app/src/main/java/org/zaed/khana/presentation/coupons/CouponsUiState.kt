package org.zaed.khana.presentation.coupons

import org.zaed.khana.data.model.Coupon

data class CouponsUiState(
    val currentUserId: String = "",
    val coupons: List<Coupon> = emptyList()
)

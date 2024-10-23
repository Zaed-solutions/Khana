package org.zaed.khana.presentation.coupons

import org.zaed.khana.data.model.Coupon

data class CouponsUiState(
    val isLoading: Boolean = true,
    val coupons: List<Coupon> = emptyList()
)

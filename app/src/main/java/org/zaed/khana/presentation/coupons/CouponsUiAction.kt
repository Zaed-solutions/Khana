package org.zaed.khana.presentation.coupons

interface CouponsUiAction {
    data object OnBackPressed: CouponsUiAction
    data class OnCopyCouponCode(val code: String): CouponsUiAction
}
package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Coupon
import org.zaed.khana.data.util.CouponResult
import org.zaed.khana.data.util.Result

interface CouponRepository {
    fun fetchCoupons(): Flow<Result<List<Coupon>, CouponResult>>
}
package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Coupon
import org.zaed.khana.data.util.CouponResult
import org.zaed.khana.data.util.Result

interface CouponRemoteDataSource {
    fun fetchCoupons(): Flow<Result<List<Coupon>, CouponResult>>
}
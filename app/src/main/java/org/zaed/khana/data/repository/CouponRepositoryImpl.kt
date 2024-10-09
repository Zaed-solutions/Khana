package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.Coupon
import org.zaed.khana.data.source.remote.CouponRemoteDataSource
import org.zaed.khana.data.util.CouponResult
import org.zaed.khana.data.util.Result

class CouponRepositoryImpl(
    private val couponRemoteDataSource: CouponRemoteDataSource
) : CouponRepository {
    override fun fetchCoupons(): Flow<Result<List<Coupon>, CouponResult>> {
        return couponRemoteDataSource.fetchCoupons()
    }
}
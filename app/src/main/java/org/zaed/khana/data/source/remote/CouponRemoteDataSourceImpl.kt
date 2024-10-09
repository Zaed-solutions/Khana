package org.zaed.khana.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zaed.khana.data.model.Coupon
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.GenericResponse
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.CouponResult
import org.zaed.khana.data.util.Result

class CouponRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : CouponRemoteDataSource {
    override fun fetchCoupons(): Flow<Result<List<Coupon>, CouponResult>> = flow {
        emit(Result.Loading)
        try {
            val request = httpClient.get {
                endPoint(EndPoint.Coupon.FetchCoupon.route)
            }
            if(request.status == HttpStatusCode.OK){
                val responseData = request.body<GenericResponse<List<Coupon>>>().data
                if(responseData != null){
                    emit(Result.Success(responseData))
                } else {
                    emit(Result.Error(CouponResult.FAILED_TO_FETCH_COUPONS))
                }
            } else {
                emit(Result.Error(CouponResult.SERVER_ERROR))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(CouponResult.SERVER_ERROR))
        }
    }
}
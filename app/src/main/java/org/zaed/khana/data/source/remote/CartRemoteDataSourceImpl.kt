package org.zaed.khana.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.source.remote.model.request.CartRequest
import org.zaed.khana.data.source.remote.model.request.ProductRequest
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.CartResult
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result

class CartRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : CartRemoteDataSource {
    override suspend fun fetchPromoCodeDiscountPercentage(request: CartRequest.FetchPromoCodeDiscountPercentage): Result<Float, CartResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Cart.FetchPromoCodeDiscountPercentage.route)
            }
            if(response.status == HttpStatusCode.OK){
                Result.success(response.body<Float>())
            } else {
                Result.failure(CartResult.APPLY_PROMO_CODE_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(CartResult.SERVER_ERROR)
        }
    }

    override suspend fun updateItemQuantity(request: CartRequest.UpdateItemQuantity): Result<Unit, CartResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Cart.UpdateItemQuantity.route)
            }
            if(response.status == HttpStatusCode.OK){
                Result.success(Unit)
            } else {
                Result.failure(CartResult.UPDATE_ITEM_QUANTITY_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(CartResult.SERVER_ERROR)
        }
    }

    override suspend fun removeCartItem(request: CartRequest.RemoveCartItem): Result<Unit, CartResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Cart.RemoveCartItem.route)
            }
            if(response.status == HttpStatusCode.OK){
                Result.success(Unit)
            } else {
                Result.failure(CartResult.REMOVE_ITEM_FROM_CART_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(CartResult.SERVER_ERROR)
        }
    }

    override fun fetchUserCartItems(request: CartRequest.FetchUserCartItems): Flow<Result<List<CartItem>, CartResult>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint(EndPoint.Cart.FetchUserCartItems.route)
            }
            if(response.status == HttpStatusCode.OK){
                emit(Result.success(response.body<List<CartItem>>()))
            } else {
                emit(Result.failure(CartResult.FETCH_CART_ITEMS_FAILED))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(CartResult.SERVER_ERROR))
        }
    }

    override suspend fun addItemToCart(request: CartRequest.AddItemToCart): Result<Unit, CartResult> {
        return try {
            val response = httpClient.post {
                endPoint(EndPoint.Cart.AddItemToCart.route)
                setBody(request)
            }
            if(response.status == HttpStatusCode.OK){
                Result.success(Unit)
            } else {
                Result.failure(CartResult.ADD_ITEM_TO_CART_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(CartResult.SERVER_ERROR)
        }
    }

    override suspend fun fetchDeliveryFee(request: CartRequest.FetchDeliveryFee): Result<Float, CartResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Cart.FetchDeliveryFee.route)
            }
            if(response.status == HttpStatusCode.OK){
                Result.success(response.body<Float>())
            } else {
                Result.failure(CartResult.FETCH_DELIVERY_FEE_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(CartResult.SERVER_ERROR)
        }
    }
}
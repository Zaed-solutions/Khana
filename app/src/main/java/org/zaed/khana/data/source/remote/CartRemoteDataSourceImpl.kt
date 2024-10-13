package org.zaed.khana.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.source.remote.util.EndPoint
import org.zaed.khana.data.source.remote.util.GenericResponse
import org.zaed.khana.data.source.remote.util.endPoint
import org.zaed.khana.data.util.CartResult
import org.zaed.khana.data.util.Result

class CartRemoteDataSourceImpl(
    private val httpClient: HttpClient,
) : CartRemoteDataSource {
    override suspend fun applyPromoCode(promoCode: String, cartItemsIds: List<String>): Result<Float, CartResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Cart.ApplyPromoCode.route)
                parameter("promoCode", promoCode)
                parameter("cartItemsIds", cartItemsIds)
            }
            if(response.status == HttpStatusCode.OK){
                val responseData = response.body<GenericResponse<Float>>().data
                if(responseData != null){
                    Result.success(responseData)
                } else {
                    Result.failure(CartResult.APPLY_PROMO_CODE_FAILED)
                }
            } else {
                Result.failure(CartResult.APPLY_PROMO_CODE_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(CartResult.SERVER_ERROR)
        }
    }

    override suspend fun updateItemQuantity(
        cartItemId: String,
        newQuantity: Int
    ): Result<Unit, CartResult> {
        return try{
            val response = httpClient.put {
                endPoint(EndPoint.Cart.UpdateItemQuantity.route)
                parameter("cartItemId", cartItemId)
                parameter("newQuantity", newQuantity)
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

    override suspend fun removeCartItem(cartItemId: String): Result<Unit, CartResult> {
        return try{
            val response = httpClient.delete {
                endPoint(EndPoint.Cart.RemoveCartItem.route)
                parameter("cartItemId", cartItemId)
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

    override fun fetchUserCartItems(userId: String): Flow<Result<List<CartItem>, CartResult>> = flow {
        emit(Result.Loading)
        try {
            val response = httpClient.get {
                endPoint(EndPoint.Cart.FetchUserCartItems.route)
                parameter("userId", userId)
            }
            if(response.status == HttpStatusCode.OK){
                val responseData = response.body<GenericResponse<List<CartItem>>>().data
                if(responseData != null){
                    emit(Result.success(responseData))
                } else {
                    emit(Result.failure(CartResult.FETCH_CART_ITEMS_FAILED))
                }
            } else {
                emit(Result.failure(CartResult.FETCH_CART_ITEMS_FAILED))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(CartResult.SERVER_ERROR))
        }
    }

    override suspend fun addItemToCart(
        productId: String,
        userId: String,
        productColor: Color,
        productSize: String
    ): Result<Unit, CartResult> {
        return try {
            val response = httpClient.post {
                endPoint(EndPoint.Cart.AddItemToCart.route)
                parameter("productId", productId)
                parameter("userId", userId)
                parameter("productColorName", productColor.name)
                parameter("productColorHex", productColor.hex)
                parameter("productSize", productSize)
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

    override suspend fun fetchDeliveryFee(userId: String): Result<Float, CartResult> {
        return try{
            val response = httpClient.get {
                endPoint(EndPoint.Cart.FetchDeliveryFee.route)
            }
            if(response.status == HttpStatusCode.OK){
                val responseData = response.body<GenericResponse<Float>>().data
                if(responseData != null){
                    Result.success(responseData)
                } else {
                    Result.failure(CartResult.FETCH_DELIVERY_FEE_FAILED)
                }
            } else {
                Result.failure(CartResult.FETCH_DELIVERY_FEE_FAILED)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(CartResult.SERVER_ERROR)
        }
    }
}
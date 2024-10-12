package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.util.CartResult
import org.zaed.khana.data.util.Result

interface CartRemoteDataSource {
    suspend fun addItemToCart(
        productId: String,
        userId: String,
        productColor: Color,
        productSize: String
    ): Result<Unit, CartResult>

    suspend fun applyPromoCode(promoCode: String, cartItemsIds: List<String>): Result<Float, CartResult>
    suspend fun updateItemQuantity(cartItemId: String, newQuantity: Int): Result<Unit, CartResult>
    suspend fun removeCartItem(cartItemId: String): Result<Unit, CartResult>
    fun fetchUserCartItems(userId: String): Flow<Result<List<CartItem>, CartResult>>
    suspend fun fetchDeliveryFee(userId: String): Result<Float, CartResult>
}
package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.util.CartResult
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result

interface CartRepository {
    suspend fun addItemToCart(userId: String, productId: String, productColor: Color, productSize: String): Result<Unit, CartResult>
    suspend fun fetchPromoCodeDiscountPercentage(promoCode: String): Result<Float, CartResult>
    suspend fun fetchDeliverFee(userId: String): Result<Float, CartResult>
    suspend fun updateItemQuantity(cartItemId: String, newQuantity: Int): Result<Unit, CartResult>
    suspend fun removeCartItem(cartItemId: String): Result<Unit, CartResult>
    fun fetchUserCartItems(userId: String): Flow<Result<List<CartItem>, CartResult>>
}
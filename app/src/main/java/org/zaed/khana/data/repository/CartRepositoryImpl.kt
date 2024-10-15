package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.source.remote.CartRemoteDataSource
import org.zaed.khana.data.util.CartResult
import org.zaed.khana.data.util.Result

class CartRepositoryImpl(
    private val cartRemoteSource: CartRemoteDataSource
) : CartRepository {

    override suspend fun addItemToCart(
        userId: String,
        productId: String,
        productColor: Color,
        productSize: String
    ): Result<Unit, CartResult> {
        return cartRemoteSource.addItemToCart(productId = productId, userId = userId, productColor = productColor, productSize = productSize)
    }
    override suspend fun applyPromoCode(promoCode: String, cartItemsIds: List<String>): Result<Float, CartResult> {
        return cartRemoteSource.applyPromoCode(promoCode, cartItemsIds)
    }

    override suspend fun fetchDeliverFee(userId: String): Result<Float, CartResult> {
        return cartRemoteSource.fetchDeliveryFee(userId)
    }

    override suspend fun updateItemQuantity(
        cartItemId: String,
        newQuantity: Int
    ): Result<Unit, CartResult> {
        return cartRemoteSource.updateItemQuantity(cartItemId, newQuantity)
    }

    override suspend fun removeCartItem(cartItemId: String): Result<Unit, CartResult> {
        return cartRemoteSource.removeCartItem(cartItemId)
    }

    override fun fetchUserCartItems(userId: String): Flow<Result<List<CartItem>, CartResult>> {
        return cartRemoteSource.fetchUserCartItems(userId)
    }

    override suspend fun fetchOrderedCartItem(orderId: String, cartItemId: String): Result<CartItem, CartResult> {
        return cartRemoteSource.fetchOrderedCartItem(orderId, cartItemId)
    }
}
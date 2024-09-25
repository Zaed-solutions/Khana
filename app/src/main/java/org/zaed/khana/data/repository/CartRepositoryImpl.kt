package org.zaed.khana.data.repository

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.model.Color
import org.zaed.khana.data.source.remote.CartRemoteDataSource
import org.zaed.khana.data.source.remote.model.request.CartRequest
import org.zaed.khana.data.source.remote.model.request.ProductRequest
import org.zaed.khana.data.util.CartResult
import org.zaed.khana.data.util.ProductResult
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
        val request = CartRequest.AddItemToCart(productId = productId, userId = userId, productColor = productColor, productSize = productSize)
        return cartRemoteSource.addItemToCart(request)
    }
    override suspend fun fetchPromoCodeDiscountPercentage(promoCode: String): Result<Float, CartResult> {
        val request = CartRequest.FetchPromoCodeDiscountPercentage(promoCode)
        return cartRemoteSource.fetchPromoCodeDiscountPercentage(request)
    }

    override suspend fun fetchDeliverFee(userId: String): Result<Float, CartResult> {
        val request = CartRequest.FetchDeliveryFee(userId)
        return cartRemoteSource.fetchDeliveryFee(request)
    }

    override suspend fun updateItemQuantity(
        cartItemId: String,
        newQuantity: Int
    ): Result<Unit, CartResult> {
        val request = CartRequest.UpdateItemQuantity(cartItemId, newQuantity)
        return cartRemoteSource.updateItemQuantity(request)
    }

    override suspend fun removeCartItem(cartItemId: String): Result<Unit, CartResult> {
        val request = CartRequest.RemoveCartItem(cartItemId)
        return cartRemoteSource.removeCartItem(request)
    }

    override fun fetchUserCartItems(userId: String): Flow<Result<List<CartItem>, CartResult>> {
        val request = CartRequest.FetchUserCartItems(userId)
        return cartRemoteSource.fetchUserCartItems(request)
    }
}
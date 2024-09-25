package org.zaed.khana.data.source.remote

import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.CartItem
import org.zaed.khana.data.source.remote.model.request.CartRequest
import org.zaed.khana.data.source.remote.model.request.ProductRequest
import org.zaed.khana.data.util.CartResult
import org.zaed.khana.data.util.ProductResult
import org.zaed.khana.data.util.Result

interface CartRemoteDataSource {
    suspend fun addItemToCart(request: CartRequest.AddItemToCart): Result<Unit, CartResult>
    suspend fun fetchPromoCodeDiscountPercentage(request: CartRequest.FetchPromoCodeDiscountPercentage): Result<Float, CartResult>
    suspend fun updateItemQuantity(request: CartRequest.UpdateItemQuantity): Result<Unit, CartResult>
    suspend fun removeCartItem(request: CartRequest.RemoveCartItem): Result<Unit, CartResult>
    fun fetchUserCartItems(request: CartRequest.FetchUserCartItems): Flow<Result<List<CartItem>, CartResult>>
    suspend fun fetchDeliveryFee(request: CartRequest.FetchDeliveryFee): Result<Float, CartResult>
}
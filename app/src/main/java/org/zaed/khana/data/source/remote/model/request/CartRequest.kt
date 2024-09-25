package org.zaed.khana.data.source.remote.model.request

import kotlinx.serialization.Serializable
import org.zaed.khana.data.model.Color

sealed interface CartRequest {
    @Serializable
    data class FetchPromoCodeDiscountPercentage(val promoCode: String): CartRequest
    @Serializable
    data class UpdateItemQuantity(val cartItemId: String, val newQuantity: Int): CartRequest
    @Serializable
    data class RemoveCartItem(val cartItemId: String): CartRequest
    @Serializable
    data class FetchUserCartItems(val userId: String): CartRequest
    @Serializable
    data class FetchDeliveryFee(val userId: String): CartRequest
    @Serializable
    data class AddItemToCart(val productId: String, val userId: String, val productColor: Color, val productSize: String): CartRequest
}
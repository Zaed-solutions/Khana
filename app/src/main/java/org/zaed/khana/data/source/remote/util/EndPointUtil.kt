package org.zaed.khana.data.source.remote.util

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import org.zaed.khana.BuildConfig

fun HttpRequestBuilder.endPoint(path: String) {
    val baseUrl = BuildConfig.BASE_URL
    url {
        takeFrom(baseUrl)
        path(path)
        contentType(ContentType.Application.Json)
    }
}

sealed class EndPoint(val route: String) {
    object Advertisement {
        data object FetchAdvertisement : EndPoint("/advertisements/all")
    }

    object Cart {
        data object AddItemToCart : EndPoint("/cart/addToCart")
        data object FetchPromoCodeDiscountPercentage : EndPoint("/cart/promoCodeDiscountPercentage")
        data object UpdateItemQuantity : EndPoint("/cart/updateItemQuantity")
        data object RemoveCartItem : EndPoint("/cart/removeCartItem")
        data object FetchUserCartItems : EndPoint("/cart/userCartItems")
        data object FetchDeliveryFee : EndPoint("/cart/deliveryFee")
    }

    object Category {
        data object FetchCategories : EndPoint("/categories/all")
    }

    object Coupon {
        data object FetchCoupon : EndPoint("/coupons/all")
    }

    object Product {
        data object FetchWishlistedProducts : EndPoint("/products/wishListProductsByUserId")
        data object FetchFlashSaleEndTime : EndPoint("/products/FlashSaleEndTime")
        data object FetchLabels : EndPoint("/products/labels")
        data object FetchProductsByLabel : EndPoint("/products/byLabel")
        data object FetchWishlistedProductsIds :
            EndPoint("/products/wishListProductsIdByUserId")

        data object AddWishlistedProduct : EndPoint("/products/WishlistedProduct")
        data object RemoveWishlistedProduct : EndPoint("/products/WishlistedProduct")
        data object CheckIfProductIsWishlisted :
            EndPoint("/products/checkIfIsProductWishlisted")
        data object SearchProductsByName : EndPoint("/products/byName")
        data object FetchProductById : EndPoint("/products/byId")
    }
}
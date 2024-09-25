package org.zaed.khana.data.source.remote.util

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom

fun HttpRequestBuilder.endPoint(path: String) {
    url {
        takeFrom("TODO()")
        path(path)
        contentType(ContentType.Application.Json)
    }
}

sealed class EndPoint(val route: String) {
    object Advertisement {
        data object FetchAdvertisement: EndPoint("/fetchAdvertisements")
    }
    object Cart {
        data object AddItemToCart: EndPoint("/addItemToCart")
        data object FetchPromoCodeDiscountPercentage: EndPoint("/fetchPromoCodeDiscountPercentage")
        data object UpdateItemQuantity: EndPoint("/updateItemQuantity")
        data object RemoveCartItem: EndPoint("/removeCartItem")
        data object FetchUserCartItems: EndPoint("/fetchUserCartItems")
        data object FetchDeliveryFee: EndPoint("/fetchDeliveryFee")
    }
    object Category{
        data object FetchCategories: EndPoint("/fetchCategories")
    }
    object Product{
        data object FetchFlashSaleEndTime: EndPoint("/fetchFlashSaleEndTime")
        data object CheckIfProductIsWishlisted: EndPoint("/CheckIfProductIsWishlisted")
        data object  FetchLabels: EndPoint("/fetchLabels")
        data object FetchProductsByLabel: EndPoint("/fetchProductsByLabel")
        data object FetchProductById: EndPoint("/fetchProductById")
        data object FetchWishlistedProductsIds: EndPoint("/fetchWishlistedProductsIds")
        data object AddWishlistedProduct: EndPoint("/addWishlistedProduct")
        data object RemoveWishlistedProduct: EndPoint("/removeWishlistedProduct")
    }
}
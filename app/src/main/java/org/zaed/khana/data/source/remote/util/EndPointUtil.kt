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
        data object FetchAdvertisement: EndPoint("/advertisements/all")
    }
    object Category{
        data object FetchCategories: EndPoint("/categories/all")
    }
    object Product{
        data object FetchFlashSaleEndTime: EndPoint("/fetchFlashSaleEndTime")
        data object  FetchLabels: EndPoint("/fetchLabels")
        data object FetchProductsByLabel: EndPoint("/fetchProductsByLabel")
        data object FetchWishlistedProductsIds: EndPoint("/fetchWishlistedProductsIds")
        data object AddWishlistedProduct: EndPoint("/addWishlistedProduct")
        data object RemoveWishlistedProduct: EndPoint("/removeWishlistedProduct")
    }
}
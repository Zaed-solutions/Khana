package org.zaed.khana.app.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.zaed.khana.data.model.ProductFilter

object CustomNavType {
    val ProductFilterType = object: NavType<ProductFilter>(
        isNullableAllowed = false,
    ){
        override fun get(bundle: Bundle, key: String): ProductFilter? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): ProductFilter {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: ProductFilter): String {
            return Uri.encode(Json.encodeToString(value))
        }
        override fun put(bundle: Bundle, key: String, value: ProductFilter) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }
}
package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShippingAddress(
    val title: String = "",
    val country: String = "",
    val city: String = "",
    val addressLine: String = "",
    val phoneNumber: String = "",
){
    fun getDisplayAddress(): String {
        return "$addressLine, $city, $country"
    }
}

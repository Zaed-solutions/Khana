package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShippingAddress(
    val title: String = "",
    val country: String = "",
    val governorate: String = "",
    val district: String = "",
    val area: String = "",
    val streetName: String = "",
    val buildingNumber: String = "",
    val nearestLandmark: String = "",
    val additionalNotes: String = "",
    val phoneNumber: String = "",
    val fullName: String = ""
){
    fun getDisplayAddress(): String {
        return "$buildingNumber $streetName, $area, $district, $governorate, $country"
    }
}

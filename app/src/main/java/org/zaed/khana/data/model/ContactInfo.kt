package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ContactInfo(
    val customerSupportLines: List<String> = emptyList(),
    val whatsappNumbers: List<String> = emptyList(),
    val websiteUrls: List<String> = emptyList(),
    val facebookPagesLinks: List<String> = emptyList(),
    val twitterProfiles: List<String> = emptyList(),
    val instagramPages: List<String> = emptyList(),
)

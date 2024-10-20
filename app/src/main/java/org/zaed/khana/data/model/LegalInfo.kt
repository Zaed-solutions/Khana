package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LegalInfo(
    val privacyPolicy: String = "",
    val termsAndConditions: String = ""
)

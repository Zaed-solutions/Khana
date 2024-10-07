package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Color(
    val name: String = "",
    val hex: String = "#000000",
)

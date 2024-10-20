package org.zaed.khana.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FAQ(
    val tag: String = "",
    val question: String = "",
    val answer: String = ""
)

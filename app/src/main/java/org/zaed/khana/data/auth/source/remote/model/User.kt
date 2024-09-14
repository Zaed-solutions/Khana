package org.zaed.khana.data.auth.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val name: String = "",
    val profilePictureURL: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val createdAt: Long = 0L,
    val lastLoginAt: Long = 0L,

)



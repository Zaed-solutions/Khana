package org.zaed.khana.data.auth.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
val id: String = "",
val avatar: String = "",
val firstName: String = "",
val lastName: String = "",
val username: String = "",
val email: String = "",
val providerName: String = "",
val password: String = "",
val birthOfDate: String = "",
val phoneNumber: String = "",
val createdAt: Long = 0L,
val deletedAt: Long = 0L,
)



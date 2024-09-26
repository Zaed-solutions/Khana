package org.zaed.khana.data.auth.source.remote.mapper

import com.google.firebase.auth.FirebaseUser
import org.zaed.khana.data.auth.source.remote.model.User

fun FirebaseUser.toUser(): User =
    User(
        id = uid,
        avatar =photoUrl.toString(),
        username = displayName.toString(),
        email = email.toString(),
        phoneNumber = phoneNumber.toString(),
        createdAt = metadata?.creationTimestamp?:0L,
        providerName =  providerData[1].providerId
    )
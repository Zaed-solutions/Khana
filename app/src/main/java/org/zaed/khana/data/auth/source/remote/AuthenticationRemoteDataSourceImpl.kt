package org.zaed.khana.data.auth.source.remote

import android.net.Uri
import android.util.Log
import org.zaed.khana.data.auth.source.remote.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.GenericResponse


class AuthenticationRemoteDataSourceImpl(
    private val auth: FirebaseAuth,
    private val server: HttpClient,
) : AuthenticationRemoteDataSource {
    override fun sendPasswordResetEmail(email: String): Flow<org.zaed.khana.data.util.Result<AuthResults, AuthResults>> =
        callbackFlow {

        }

    override fun signInWithEmail(
        email: String,
        password: String
    ): Flow<org.zaed.khana.data.util.Result<User, AuthResults>> =
        callbackFlow {
            auth.signInWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    trySend(
                        org.zaed.khana.data.util.Result.Success(
                            User(
                                id = user?.uid ?: "",
                                username = user?.displayName ?: "",
                                avatar = user?.photoUrl.toString(),
                                email = user?.email ?: "",
                                phoneNumber = user?.phoneNumber ?: "",
                                createdAt = user?.metadata?.creationTimestamp?:0L,
                            )
                        )
                    )
                } else {
                    trySend(org.zaed.khana.data.util.Result.Error(AuthResults.LOGIN_FAILED))
                }
            }
            awaitClose()
        }

    override fun signUpWithEmail(
        name: String,
        avatarByteArray: Uri,
        email: String,
        password: String
    ): Flow<org.zaed.khana.data.util.Result<User, AuthResults>> = callbackFlow {
        org.zaed.khana.data.util.Result.Loading
        var uri = Uri.EMPTY
//        if (avatarByteArray != Uri.EMPTY) {
//            uri = Firebase.storage.reference.child("avatars").putFile(avatarByteArray)
//                .await().storage.downloadUrl.await()
//        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.updateProfile(
                    com.google.firebase.auth.UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .setPhotoUri(uri)
                        .build()
                )
                val user = auth.currentUser
                trySend(
                    org.zaed.khana.data.util.Result.Success(
                        User(
                            id = user?.uid ?: "",
                            username = user?.displayName ?: "",
                            avatar = user?.photoUrl.toString(),
                            email = user?.email ?: "",
                            phoneNumber = user?.phoneNumber ?: "",
                            createdAt = user?.metadata?.creationTimestamp?:0L,
                        )
                    )
                )

            } else {
                trySend(org.zaed.khana.data.util.Result.Error(AuthResults.SERVER_ERROR))
            }
        }
        awaitClose()
    }

    override suspend fun getSignedInUser(): org.zaed.khana.data.util.Result<User, AuthResults> {
        if (Firebase.auth.currentUser == null) {
            return org.zaed.khana.data.util.Result.Error(AuthResults.LOGIN_FAILED)
        } else {
            val user = auth.currentUser
            return org.zaed.khana.data.util.Result.Success(
                User(
                    id = user?.uid ?: "",
                    username = user?.displayName ?: "",
                    avatar = user?.photoUrl.toString(),
                    email = user?.email ?: "",
                    phoneNumber = user?.phoneNumber ?: "",
                    createdAt = user?.metadata?.creationTimestamp?:0L,
                )
            )
        }
    }

    override suspend fun logout(): org.zaed.khana.data.util.Result<AuthResults, AuthResults> {
        auth.signOut()
        return org.zaed.khana.data.util.Result.Success(AuthResults.LOGOUT_SUCCESS)
    }

    override suspend fun deleteAccount(userId: String): org.zaed.khana.data.util.Result<Unit, AuthResults> {
        auth.currentUser?.delete()
        return org.zaed.khana.data.util.Result.Success(Unit)
    }

    override suspend fun saveUser(user: User) {
        val response = server.post {
            url("http://192.168.1.4:8080/users/insert")
            setBody(user)
            contentType(ContentType.Application.Json)
        }.body<GenericResponse<Unit>>()
        println(response)
    }
}
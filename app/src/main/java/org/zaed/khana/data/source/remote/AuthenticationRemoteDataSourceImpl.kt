package org.zaed.khana.data.source.remote

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import org.zaed.khana.BuildConfig.BASE_URL
import org.zaed.khana.data.model.User
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.GenericResponse
import org.zaed.khana.data.util.Result


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

    override suspend fun verifyPassword(password: String): Result<Boolean, AuthResults> {
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        currentUser?.let {
            val email = it.email
            if (email != null) {
                val credential = EmailAuthProvider.getCredential(email, password)
                return try {
                    it.reauthenticate(credential).await()
                    Result.success(true)
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    Result.Success(false)
                } catch (e: Exception) {
                    Result.Error(AuthResults.SERVER_ERROR)
                }
            }
        }
        return Result.Error(AuthResults.USER_NOT_FOUND)
    }

    override fun signUpWithEmail(
        name: String,
        email: String,
        password: String
    ): Flow<org.zaed.khana.data.util.Result<FirebaseUser, AuthResults>> = callbackFlow {
        println("inside signup with email$email")
        trySend(org.zaed.khana.data.util.Result.Loading)
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.updateProfile(
                    com.google.firebase.auth.UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                )
                val user = auth.currentUser
                trySend(
                    org.zaed.khana.data.util.Result.Success(
                        user!!
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
            url("$BASE_URL/users/insert")
            setBody(user)
            contentType(ContentType.Application.Json)
        }.body<GenericResponse<Unit>>()
        println(response)
    }

    override suspend fun sendOtp(email: String):Boolean {
        println(email)
        val url = BASE_URL +"users/sendOtp"
        println(url)
        val response = server.get {
            url(url)
            parameter("email",email)
        }.body<GenericResponse<Boolean>>()
        return response.data ?: false
    }

    override suspend fun verifyCode(fullOtp: String, email: String):Boolean {
            println(email + fullOtp)
        val url = BASE_URL +"users/verifyOtp"
        println(url)
        val response = server.get {
            url(url)
            parameter("email", email)
            parameter("otp", fullOtp)
        }.body<GenericResponse<Boolean>>()
        return response.data ?: false
    }

    override suspend fun updateUserPassword(newPassword: String): Result<Unit, AuthResults> {
        try{
            val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
            user?.let {
                it.updatePassword(newPassword).await()
                return Result.success(Unit)
            }
            return Result.Error(AuthResults.USER_NOT_FOUND)
        } catch (e: Exception){
            e.printStackTrace()
            return Result.Error(AuthResults.SERVER_ERROR)
        }
    }
}
package org.zaed.khana.data.source.remote

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.model.User
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.Result

interface AuthenticationRemoteDataSource {
    fun sendPasswordResetEmail(email: String): Flow<Result<AuthResults, AuthResults>>
    fun signInWithEmail(
        email: String,
        password: String
    ): Flow<Result<User, AuthResults>>

    fun signUpWithEmail(
        name: String,
        email: String,
        password: String
    ): Flow<Result<FirebaseUser, AuthResults>>

    suspend fun getSignedInUser(): Result<User, AuthResults>
    suspend fun logout(): Result<AuthResults, AuthResults>
    suspend fun deleteAccount(userId: String): Result<Unit, AuthResults>
    suspend fun saveUser(user: User)
    suspend fun sendOtp(email: String): Boolean
    suspend fun verifyCode(fullOtp: String, email: String): Boolean


}
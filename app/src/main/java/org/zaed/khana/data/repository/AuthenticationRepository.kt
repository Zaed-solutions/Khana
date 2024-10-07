package org.zaed.khana.data.repository

import com.google.firebase.auth.FirebaseUser
import org.zaed.khana.data.model.User
import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.Result

interface AuthenticationRepository {
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
    suspend fun saveUser(user: FirebaseUser?)


}

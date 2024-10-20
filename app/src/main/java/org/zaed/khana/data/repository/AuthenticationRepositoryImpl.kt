package org.zaed.khana.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import org.zaed.khana.data.source.remote.AuthenticationRemoteDataSource
import org.zaed.khana.data.model.User
import kotlinx.coroutines.flow.Flow
import org.zaed.khana.data.source.remote.model.mapper.toUser
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.Result

class AuthenticationRepositoryImpl(
    private val remoteDataSource: AuthenticationRemoteDataSource,
) : AuthenticationRepository {
    override fun sendPasswordResetEmail(email: String) =
        remoteDataSource.sendPasswordResetEmail(email)

    override fun signInWithEmail(
        email: String,
        password: String
    ): Flow<Result<User, AuthResults>> =
        remoteDataSource.signInWithEmail(email, password)

    override fun signUpWithEmail(
        name: String,
        email: String,
        password: String
    ): Flow<Result<FirebaseUser, AuthResults>> =
        remoteDataSource.signUpWithEmail(name, email, password)

    override suspend fun getSignedInUser(): Result<User, AuthResults> =
        remoteDataSource.getSignedInUser()

    override suspend fun logout(): Result<AuthResults, AuthResults> {
        return remoteDataSource.logout()
    }

    override suspend fun deleteAccount(userId: String): Result<Unit, AuthResults> {
        return remoteDataSource.deleteAccount(userId).also {
            if (it is Result.Success) {
                logout()
            }
        }
    }

    override suspend fun saveUser(firebaseUser: FirebaseUser?) {
        if (firebaseUser == null) return
        remoteDataSource.saveUser(firebaseUser.toUser())
    }

    override suspend fun sendOtp(email: String)
    =    remoteDataSource.sendOtp(email)

    override suspend fun verifyCode(fullOtp: String, email: String) =
        remoteDataSource.verifyCode(fullOtp, email)

    override suspend fun updateUserProfile(name: String, phoneNumber: String, imageUri: Uri?): Flow<Result<AuthResults, AuthResults>> =
        remoteDataSource.updateUserProfile(name, phoneNumber, imageUri)


}

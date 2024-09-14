package org.zaed.khana.data.auth.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import org.zaed.khana.data.auth.source.remote.AuthenticationRemoteDataSource
import org.zaed.khana.data.auth.source.remote.model.User
import kotlinx.coroutines.flow.Flow
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
        avatarByteArray: Uri,
        email: String,
        password: String
    ): Flow<Result<User, AuthResults>> =
        remoteDataSource.signUpWithEmail(name, avatarByteArray, email, password)

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
        val user = User(
            id = firebaseUser.uid,
            avatar = firebaseUser.photoUrl.toString(),
            username = firebaseUser.displayName.toString(),
            email = firebaseUser.email.toString(),
            phoneNumber = firebaseUser.phoneNumber.toString(),
            createdAt = firebaseUser.metadata?.creationTimestamp?:0L,
            providerName =  firebaseUser.providerData[1].providerId
        )
        remoteDataSource.saveUser(user)
    }


}

package org.zaed.khana.app.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module
import org.zaed.khana.data.auth.source.remote.AuthenticationRemoteDataSource
import org.zaed.khana.data.auth.source.remote.AuthenticationRemoteDataSourceImpl

val remoteDataSourceModule = module {
    single <AuthenticationRemoteDataSource>{ AuthenticationRemoteDataSourceImpl(get()) }
    single<FirebaseAuth> {
        Firebase.auth
    }
}
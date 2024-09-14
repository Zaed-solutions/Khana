package org.zaed.khana.app.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.zaed.khana.data.auth.source.remote.AuthenticationRemoteDataSource
import org.zaed.khana.data.auth.source.remote.AuthenticationRemoteDataSourceImpl

val remoteDataSourceModule = module {
    single <AuthenticationRemoteDataSource>{ AuthenticationRemoteDataSourceImpl(get(),get()) }
    single<FirebaseAuth> {
        Firebase.auth
    }
    single<HttpClient>{
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    isLenient = true
                    encodeDefaults = true
                })
            }
        }
    }
}
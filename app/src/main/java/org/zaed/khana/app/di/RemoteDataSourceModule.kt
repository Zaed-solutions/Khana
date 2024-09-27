package org.zaed.khana.app.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.zaed.khana.data.source.remote.AdvertisementRemoteDataSource
import org.zaed.khana.data.source.remote.AdvertisementRemoteDataSourceImpl
import org.zaed.khana.data.source.remote.CategoryRemoteDataSource
import org.zaed.khana.data.source.remote.CategoryRemoteDataSourceImpl
import org.zaed.khana.data.source.remote.CouponRemoteDataSource
import org.zaed.khana.data.source.remote.CouponRemoteDataSourceImpl
import org.zaed.khana.data.source.remote.ProductRemoteDataSource
import org.zaed.khana.data.source.remote.ProductRemoteDataSourceImpl
import kotlin.time.Duration.Companion.seconds

val remoteDataSourceModule = module {
    single<AdvertisementRemoteDataSource> { AdvertisementRemoteDataSourceImpl(get()) }
    single<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(get()) }
    single<ProductRemoteDataSource> { ProductRemoteDataSourceImpl(get()) }
    single<CouponRemoteDataSource> { CouponRemoteDataSourceImpl(get()) }
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    isLenient = true
                    encodeDefaults = true
                })
            }
            install(WebSockets) {
                pingInterval = 30.seconds.inWholeMilliseconds
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
        }
    }
}
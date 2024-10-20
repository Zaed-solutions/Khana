package org.zaed.khana.app.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.zaed.khana.data.source.remote.AdvertisementRemoteDataSource
import org.zaed.khana.data.source.remote.AdvertisementRemoteDataSourceImpl
import org.zaed.khana.data.source.remote.AuthenticationRemoteDataSource
import org.zaed.khana.data.source.remote.AuthenticationRemoteDataSourceImpl
import org.zaed.khana.data.source.remote.CartRemoteDataSource
import org.zaed.khana.data.source.remote.CartRemoteDataSourceImpl
import org.zaed.khana.data.source.remote.CategoryRemoteDataSource
import org.zaed.khana.data.source.remote.CategoryRemoteDataSourceImpl
import org.zaed.khana.data.source.remote.CouponRemoteDataSource
import org.zaed.khana.data.source.remote.CouponRemoteDataSourceImpl
import org.zaed.khana.data.source.remote.OrderRemoteDataSource
import org.zaed.khana.data.source.remote.OrderRemoteDataSourceImpl
import org.zaed.khana.data.source.remote.ProductRemoteDataSource
import org.zaed.khana.data.source.remote.ProductRemoteDataSourceImpl
import org.zaed.khana.data.source.remote.ShippingAddressRemoteDataSource
import org.zaed.khana.data.source.remote.ShippingAddressRemoteDataSourceImpl
import org.zaed.khana.data.source.remote.SupportRemoteDataSource
import org.zaed.khana.data.source.remote.SupportRemoteDataSourceImpl
import kotlin.time.Duration.Companion.seconds

val remoteDataSourceModule = module {
    single <AuthenticationRemoteDataSource>{ AuthenticationRemoteDataSourceImpl(get(),get(),get()) }
    single<FirebaseAuth> {
        Firebase.auth
    }
    single<FirebaseStorage> {
        Firebase.storage
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
    single<AdvertisementRemoteDataSource> { AdvertisementRemoteDataSourceImpl(get()) }
    single<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(get()) }
    single<ProductRemoteDataSource> { ProductRemoteDataSourceImpl(get()) }
    single<CouponRemoteDataSource> { CouponRemoteDataSourceImpl(get()) }
    single<CartRemoteDataSource> { CartRemoteDataSourceImpl(get()) }
    single<ShippingAddressRemoteDataSource> { ShippingAddressRemoteDataSourceImpl(get()) }
    single<OrderRemoteDataSource> { OrderRemoteDataSourceImpl(get()) }
    single<SupportRemoteDataSource> { SupportRemoteDataSourceImpl(get()) }
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
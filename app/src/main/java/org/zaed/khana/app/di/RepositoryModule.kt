package org.zaed.khana.app.di

import org.koin.dsl.module
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.repository.AuthenticationRepositoryImpl
import org.zaed.khana.data.repository.AdvertisementRepository
import org.zaed.khana.data.repository.AdvertisementRepositoryImpl
import org.zaed.khana.data.repository.CartRepository
import org.zaed.khana.data.repository.CartRepositoryImpl
import org.zaed.khana.data.repository.CategoryRepository
import org.zaed.khana.data.repository.CategoryRepositoryImpl
import org.zaed.khana.data.repository.CouponRepository
import org.zaed.khana.data.repository.CouponRepositoryImpl
import org.zaed.khana.data.repository.OrderRepository
import org.zaed.khana.data.repository.OrderRepositoryImpl
import org.zaed.khana.data.repository.ShippingAddressRepository
import org.zaed.khana.data.repository.ShippingAddressRepositoryImpl
import org.zaed.khana.data.repository.ProductRepository
import org.zaed.khana.data.repository.ProductRepositoryImpl
import org.zaed.khana.data.repository.SearchRepository
import org.zaed.khana.data.repository.SearchRepositoryImpl

val repositoryModule = module {
    single <AuthenticationRepository>{ AuthenticationRepositoryImpl(get()) }

    single<AdvertisementRepository> { AdvertisementRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<CouponRepository> { CouponRepositoryImpl(get()) }
    single<CartRepository> { CartRepositoryImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
    single<ShippingAddressRepository> { ShippingAddressRepositoryImpl(get()) }
    single<OrderRepository> { OrderRepositoryImpl(get()) }
}
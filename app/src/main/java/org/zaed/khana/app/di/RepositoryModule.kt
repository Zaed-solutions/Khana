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
import org.zaed.khana.data.repository.CheckoutRepository
import org.zaed.khana.data.repository.CheckoutRepositoryImpl
import org.zaed.khana.data.repository.ProductRepository
import org.zaed.khana.data.repository.ProductRepositoryImpl

val repositoryModule = module {
    single <AuthenticationRepository>{ AuthenticationRepositoryImpl(get()) }

    single<AdvertisementRepository> { AdvertisementRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<CartRepository> { CartRepositoryImpl(get()) }
    single<CheckoutRepository> { CheckoutRepositoryImpl(get()) }
}
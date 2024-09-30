package org.zaed.khana.app.di

import org.koin.dsl.module
import org.zaed.khana.data.repository.AdvertisementRepository
import org.zaed.khana.data.repository.AdvertisementRepositoryImpl
import org.zaed.khana.data.repository.CategoryRepository
import org.zaed.khana.data.repository.CategoryRepositoryImpl
import org.zaed.khana.data.repository.ProductRepository
import org.zaed.khana.data.repository.ProductRepositoryImpl
import org.zaed.khana.data.repository.SearchRepository
import org.zaed.khana.data.repository.SearchRepositoryImpl

val repositoryModule = module {
    single<AdvertisementRepository> { AdvertisementRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
}
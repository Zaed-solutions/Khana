package org.zaed.khana.app.di

import io.realm.kotlin.Realm
import org.koin.dsl.module
import org.zaed.khana.app.MainApplication
import org.zaed.khana.data.source.local.RecentSearchLocalDataSource
import org.zaed.khana.data.source.local.RecentSearchLocalDataSourceImpl

val localDataSourceModule = module {
    single<RecentSearchLocalDataSource> { RecentSearchLocalDataSourceImpl(get()) }
    single<Realm> { MainApplication.realm }
}
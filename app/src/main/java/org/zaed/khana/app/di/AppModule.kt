package org.zaed.khana.app.di

import org.koin.dsl.module

val appModule = module {
    includes(viewModelModule, repositoryModule, remoteDataSourceModule, localDataSourceModule)
}
package org.zaed.khana.app.di

import org.koin.dsl.module
import org.zaed.khana.data.auth.repository.AuthenticationRepository
import org.zaed.khana.data.auth.repository.AuthenticationRepositoryImpl

val repositoryModule = module {
    single <AuthenticationRepository>{ AuthenticationRepositoryImpl(get()) }

}
package org.zaed.khana.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.zaed.khana.presentation.home.HomeViewModel

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
}
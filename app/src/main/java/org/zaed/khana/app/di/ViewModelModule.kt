package org.zaed.khana.app.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.zaed.khana.presentation.filter.FilterViewModel
import org.zaed.khana.presentation.home.HomeViewModel

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::FilterViewModel)
}
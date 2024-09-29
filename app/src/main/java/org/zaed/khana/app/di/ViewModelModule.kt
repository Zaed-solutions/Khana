package org.zaed.khana.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.zaed.khana.presentation.home.HomeViewModel
import org.zaed.khana.presentation.search.SearchViewModel

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::SearchViewModel)
}
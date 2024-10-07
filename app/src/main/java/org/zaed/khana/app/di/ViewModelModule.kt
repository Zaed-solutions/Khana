package org.zaed.khana.app.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.zaed.khana.presentation.auth.login.LoginViewModel
import org.zaed.khana.presentation.auth.signup.SignUpScreen
import org.zaed.khana.presentation.auth.signup.SignUpViewModel
import org.zaed.khana.presentation.home.HomeViewModel
import org.zaed.khana.presentation.wishlist.WishlistViewModel
import org.zaed.khana.presentation.productdetails.ProductDetailsViewModel

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ProductDetailsViewModel)
    viewModelOf(::WishlistViewModel)
}
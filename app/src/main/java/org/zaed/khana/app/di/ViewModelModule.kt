package org.zaed.khana.app.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.zaed.khana.presentation.auth.forgetPassword.ForgetPasswordViewModel
import org.zaed.khana.presentation.auth.login.LoginViewModel
import org.zaed.khana.presentation.auth.newpassword.NewPasswordViewModel
import org.zaed.khana.presentation.auth.otp.OtpViewModel
import org.zaed.khana.presentation.auth.signup.SignUpViewModel
import org.zaed.khana.presentation.auth.completeprofile.CompleteProfileViewModel
import org.zaed.khana.presentation.filter.FilterViewModel
import org.zaed.khana.presentation.home.HomeViewModel
import org.zaed.khana.presentation.productdetails.ProductDetailsViewModel
import org.zaed.khana.presentation.wishlist.WishlistViewModel
import org.zaed.khana.presentation.cart.CartViewModel
import org.zaed.khana.presentation.coupons.CouponsViewModel
import org.zaed.khana.presentation.search.SearchViewModel
import org.zaed.khana.presentation.category.CategoryViewModel
import org.zaed.khana.presentation.checkout.CheckoutViewModel
import org.zaed.khana.presentation.helpcenter.HelpCenterViewModel
import org.zaed.khana.presentation.leavereview.LeaveReviewViewModel
import org.zaed.khana.presentation.myorders.MyOrdersViewModel
import org.zaed.khana.presentation.passwordmanager.PasswordManagerViewModel
import org.zaed.khana.presentation.payment.PaymentViewModel
import org.zaed.khana.presentation.privacy.PrivacyPolicyViewModel
import org.zaed.khana.presentation.settings.SettingsViewModel
import org.zaed.khana.presentation.trackorder.TrackOrderViewModel
import org.zaed.khana.presentation.profile.ProfileViewModel

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CouponsViewModel)
    viewModelOf(::ProductDetailsViewModel)
    viewModelOf(::WishlistViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::CheckoutViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::FilterViewModel)
    viewModelOf(::CategoryViewModel)
    viewModelOf(::MyOrdersViewModel)
    viewModelOf(::LeaveReviewViewModel)
    viewModelOf(::TrackOrderViewModel)
    viewModelOf(::ForgetPasswordViewModel)
    viewModelOf(::OtpViewModel)
    viewModelOf(::HelpCenterViewModel)
    viewModelOf(::NewPasswordViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::PrivacyPolicyViewModel)
    viewModelOf(::PasswordManagerViewModel)
    viewModelOf(::CompleteProfileViewModel)
    viewModelOf(::PaymentViewModel)
    viewModelOf(::ProfileViewModel)
}
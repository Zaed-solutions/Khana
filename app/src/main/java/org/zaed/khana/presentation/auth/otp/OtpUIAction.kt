package org.zaed.khana.presentation.auth.otp

sealed interface OtpUIAction{
    data class  OnOtpChanged(val newOtp:String):OtpUIAction
    data object OnVerifyOtpClicked:OtpUIAction
    data object OnResendOtpClicked:OtpUIAction

}
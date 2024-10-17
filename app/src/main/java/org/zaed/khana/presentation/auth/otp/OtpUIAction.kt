package org.zaed.khana.presentation.auth.otp

sealed interface OtpUIAction{
    data class  OnOtp1Changed(val newOtp:String):OtpUIAction
    data class  OnOtp2Changed(val newOtp:String):OtpUIAction
    data class  OnOtp3Changed(val newOtp:String):OtpUIAction
    data class  OnOtp4Changed(val newOtp:String):OtpUIAction
    data object OnVerifyOtpClicked:OtpUIAction
    data object OnResendOtpClicked:OtpUIAction

}
package org.zaed.khana.presentation.auth.forgetPassword

sealed interface ForgetPasswordUIAction{
    data class  OnEmailChanged(val newEmail:String):ForgetPasswordUIAction
    data object OnSendOtpClicked:ForgetPasswordUIAction

}
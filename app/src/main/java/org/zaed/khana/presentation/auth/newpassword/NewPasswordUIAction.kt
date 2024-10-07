package org.zaed.khana.presentation.auth.newpassword

sealed interface NewPasswordUIAction{
    data class OnPasswordChanged(val newPass:String):NewPasswordUIAction
    data class OnConfirmPasswordChanged(val newPass:String):NewPasswordUIAction
    data object OnCreateNewPasswordClicked:NewPasswordUIAction

}
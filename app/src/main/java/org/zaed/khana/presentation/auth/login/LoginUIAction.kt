package org.zaed.khana.presentation.auth.login

sealed interface LoginUIAction{
    data class OnEmailChanged(val newEmail:String):LoginUIAction
    data class OnPasswordChanged(val newPass:String):LoginUIAction
    data object OnSignInClicked:LoginUIAction

}
package org.zaed.khana.presentation.auth.signup

sealed interface SignUpUIAction {
    data class OnEmailChanged(val newEmail:String):SignUpUIAction
    data class OnPasswordChanged(val newPass:String):SignUpUIAction
    data object OnSignUpClicked:SignUpUIAction
    data class OnNameChanged(val newName:String):SignUpUIAction
    data class OnTermsAndConditionsClicked(val approved:Boolean):SignUpUIAction

}
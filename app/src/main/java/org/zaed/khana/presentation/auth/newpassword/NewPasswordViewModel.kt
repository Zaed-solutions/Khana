package org.zaed.khana.presentation.auth.newpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.auth.repository.AuthenticationRepository
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.PasswordFieldError
import org.zaed.khana.presentation.auth.util.Validator


class NewPasswordViewModel(
    private val authRepo: AuthenticationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewPasswordUiState())
    val uiState = _uiState.asStateFlow()


    private fun updateActionResult(message: AuthResults) {
        println(message.userMessage)
        _uiState.update {
            it.copy(
                userMessage = message,
                loading = false
            )
        }
    }


    private fun updatePasswordError(message: PasswordFieldError) {
        _uiState.update {
            it.copy(
                passwordError = message,
            )
        }
    }

    private fun updateConfirmPasswordError(message: PasswordFieldError) {
        _uiState.update {
            it.copy(
                confirmPasswordError = message,
            )
        }
    }


    private fun enableLoading() {
        _uiState.update {
            it.copy(
                loading = true
            )
        }
    }


    fun handleUiState(it: NewPasswordUIAction) {
        when (it) {
            is NewPasswordUIAction.OnPasswordChanged -> updatePassword(it.newPass)
            is NewPasswordUIAction.OnConfirmPasswordChanged -> updateConfirmPassword(it.newPass)
            NewPasswordUIAction.OnCreateNewPasswordClicked -> TODO()
        }
    }

    private fun createNewPassword() {
        val password = uiState.value.password
        val confirmPassword = uiState.value.confirmPassword
        val passwordError = Validator.PasswordValidator.validateAll(password)
        val confirmPasswordError = Validator.PasswordValidator.validateAll(confirmPassword)
        if (passwordError && confirmPasswordError) {
            //TODO create new password
        }else{
            //TODO show error
        }

    }

    private fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }

    private fun updateConfirmPassword(password: String) {
        _uiState.update {
            it.copy(
                confirmPassword = password
            )
        }
    }


}
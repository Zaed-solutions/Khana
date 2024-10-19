package org.zaed.khana.presentation.passwordmanager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository

class PasswordManagerViewModel(
    private val authRepo: AuthenticationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PasswordManagerUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.value = uiState.value.copy(currentUser = user)
            }.onFailure {
                Log.e(
                    "PasswordManagerViewModel:fetchCurrentUser",
                    "Failed to fetch current user $it"
                )
            }
        }
    }

    fun handleAction(action: PasswordManagerUiAction) {
        when (action) {
            PasswordManagerUiAction.OnChangePasswordClicked -> validatePasswords()
            is PasswordManagerUiAction.OnConfirmPasswordChanged -> updateConfirmPassword(action.confirmPassword)
            is PasswordManagerUiAction.OnCurrentPasswordChanged -> updateCurrentPassword(action.currentPassword)
            is PasswordManagerUiAction.OnNewPasswordChanged -> updateNewPassword(action.newPassword)
            else -> Unit
        }
    }

    private fun updateNewPassword(newPassword: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(newPassword = newPassword, isNotMatchingPasswords = false) }
        }
    }

    private fun updateCurrentPassword(currentPassword: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentPassword = currentPassword,
                    isCurrentPasswordWrong = false
                )
            }
        }
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    confirmPassword = confirmPassword,
                    isNotMatchingPasswords = false
                )
            }
        }
    }

    private fun validatePasswords() {
        viewModelScope.launch {
            verifyMatchingPasswords()
            if (!_uiState.value.isNotMatchingPasswords) {
                authRepo.verifyPassword(_uiState.value.currentPassword)
                    .onSuccessWithData { isCorrect ->
                        if (isCorrect) {
                            updatePassword()
                        } else {
                            _uiState.update { it.copy(isCurrentPasswordWrong = true) }
                        }
                    }.onFailure { error ->
                    Log.e("PasswordManagerViewModel:validatePasswords", "$error")
                }
            }
        }
    }

    private fun updatePassword() {
        viewModelScope.launch {
            authRepo.updateUserPassword(_uiState.value.newPassword).onSuccess {
                _uiState.update { it.copy(isPasswordChanged = true) }
            }.onFailure {
                Log.e("PasswordManagerViewModel:updatePassword", "$it")
            }
        }
    }

    private fun verifyMatchingPasswords() {
        with(_uiState.value) {
            if (newPassword != confirmPassword) {
                _uiState.update { it.copy(isNotMatchingPasswords = true) }
            } else {
                _uiState.update { it.copy(isNotMatchingPasswords = true) }
            }
        }
    }
}
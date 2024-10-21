package org.zaed.khana.presentation.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository

class SettingsViewModel(
    private val authRepo: AuthenticationRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()
    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.update { it.copy(currentUser = user) }
            }.onFailure {
                Log.e("SettingsViewModel:fetchCurrentUser", "Failed to fetch current user $it")
            }
        }
    }

    fun handleAction(action: SettingsUiAction){
        when(action){
            is SettingsUiAction.OnVerifyPasswordAndDeleteAccount -> {
                verifyPasswordAndDeleteAccount(action.password)
            }
            is SettingsUiAction.OnCancelDeleteAccountClicked -> {
                _uiState.update { it.copy(isWrongPassword = false) }
            }
            else -> Unit
        }
    }

    private fun verifyPasswordAndDeleteAccount(password: String) {
       viewModelScope.launch {
           authRepo.verifyPassword(password).onSuccessWithData { isVerified ->
               if(isVerified){
                   deleteAccount()
               }else{
                   _uiState.update { it.copy(isWrongPassword = true) }
               }
           }.onFailure {
               Log.e("SettingsViewModel:verifyPasswordAndDeleteAccount", "Failed to verify password $it")
           }
       }
    }

    private fun deleteAccount(){
        viewModelScope.launch {
            authRepo.deleteAccount(uiState.value.currentUser.id).onSuccess {
                _uiState.update { it.copy(isAccountDeleted = true) }
            }.onFailure {
                Log.e("SettingsViewModel:deleteAccount", "Failed to delete account $it")
            }
        }
    }
}
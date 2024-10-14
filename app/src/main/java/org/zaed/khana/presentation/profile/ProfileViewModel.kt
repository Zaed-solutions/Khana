package org.zaed.khana.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository

class ProfileViewModel(
    private val authRepo: AuthenticationRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()
    init {
        fetchCurrentUser()
    }
    private fun fetchCurrentUser() {
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.update { it.copy(currentUser = user) }
            }.onFailure {
                Log.e("ProfileViewModel:fetchCurrentUser", "Failed to fetch user $it")
            }
        }
    }
    fun handleUiAction(action: ProfileUiAction) {
        when(action) {
            is ProfileUiAction.OnUpdateAvatar -> TODO()
            is ProfileUiAction.OnLogoutClicked -> logout()
            else -> Unit
        }
    }
    private fun logout(){
        viewModelScope.launch {
            authRepo.logout().onSuccess {
                _uiState.update { it.copy(isLoggedOut = true) }
            }.onFailure {
                Log.e("ProfileViewModel:logout", "Failed to logout $it")
            }
        }
    }
}
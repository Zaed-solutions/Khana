package org.zaed.khana.presentation.auth.completeprofile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.util.Result

class CompleteProfileViewModel(
    private val authRepo: AuthenticationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompleteProfileUiState())
    val uiState = _uiState.asStateFlow()
    init{
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.update { it.copy(currentUser = user) }
            }.onFailure {
                Log.e("CompleteProfileViewModel:fetchCurrentUser", "Failed to fetch user $it")
            }
        }
    }

    fun updateProfile(name: String, phoneNumber: String, imageUri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepo.updateUserProfile(
                userId = uiState.value.currentUser.id,
                name = name,
                phoneNumber = phoneNumber,
                imageUri = imageUri
            ).collect { result ->
             when(result){
                 is Result.Error -> {
                     //TODO Handle error
                     _uiState.update { it.copy(loading = false) }
                 }
                 Result.Loading -> _uiState.update { it.copy(loading = true) }
                 is Result.Success -> {
                     _uiState.update { it.copy(loading = false, isProfileCompleted = true) }
                 }
             }
            }
        }
    }


}

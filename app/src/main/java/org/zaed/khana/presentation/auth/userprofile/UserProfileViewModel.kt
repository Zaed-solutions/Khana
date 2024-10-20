package org.zaed.khana.presentation.auth.userprofile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.util.Result

class UserProfileViewModel(
    private val userRepository: AuthenticationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState = _uiState.asStateFlow()

    fun updateProfile(name: String, phoneNumber: String, imageUri: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUserProfile(name, phoneNumber, imageUri).collect { result ->
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

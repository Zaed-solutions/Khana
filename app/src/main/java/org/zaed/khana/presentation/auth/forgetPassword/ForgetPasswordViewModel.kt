package org.zaed.khana.presentation.auth.forgetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.ForgetPasswordResult


class ForgetPasswordViewModel(
    private val authRepo: AuthenticationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ForgetPasswordUiState())
    val uiState = _uiState.asStateFlow()
    private fun updateActionResult(message: ForgetPasswordResult) {
        println(message.userMessage)
        _uiState.update {
            it.copy(
                userMessage = message,
                loading = false
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


    fun handleUiState(it: ForgetPasswordUIAction) {
        when (it) {
            is ForgetPasswordUIAction.OnEmailChanged -> updateEmail(it.newEmail)
            ForgetPasswordUIAction.OnSendOtpClicked -> sendOtp()
        }
    }

    private fun sendOtp() {
        viewModelScope.launch(Dispatchers.IO) {
            if (uiState.value.email.isNotEmpty()) {
                when (authRepo.sendOtp(uiState.value.email)) {
                    true -> {
                        updateActionResult(ForgetPasswordResult.OTP_SENT)
                    }

                    false -> {
                        updateActionResult(ForgetPasswordResult.OTP_NOT_SENT)
                    }
                }
            }
        }
    }

    private fun updateEmail(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail)
        }
    }
}
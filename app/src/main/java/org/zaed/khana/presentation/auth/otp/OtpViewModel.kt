package org.zaed.khana.presentation.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.PasswordFieldError


class OtpViewModel(
    private val authRepo: AuthenticationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(OtpUiState())
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

    private fun updateOtpError(message: PasswordFieldError) {}

    private fun enableLoading() {
        _uiState.update {
            it.copy(
                loading = true
            )
        }
    }


    fun handleUiState(it: OtpUIAction) {
        when (it) {
            is OtpUIAction.OnOtp1Changed -> updateOtp1(it.newOtp)
            is OtpUIAction.OnOtp2Changed -> updateOtp2(it.newOtp)
            is OtpUIAction.OnOtp3Changed -> updateOtp3(it.newOtp)
            is OtpUIAction.OnOtp4Changed -> updateOtp4(it.newOtp)
            OtpUIAction.OnResendOtpClicked -> TODO()
            OtpUIAction.OnVerifyOtpClicked -> onVerifyOtpClicked()
        }
    }

    private fun onVerifyOtpClicked() {
        with(uiState.value){
            val fullOtp =otp1+otp2+otp3+otp4
            viewModelScope.launch (Dispatchers.IO){
                authRepo.verifyCode(fullOtp, email)
            }

        }
    }

    private fun updateOtp1(otp: String)  { _uiState.update { it.copy(otp1 = otp) } }
    private fun updateOtp2(otp: String)  { _uiState.update { it.copy(otp2 = otp) } }
    private fun updateOtp3(otp: String)  { _uiState.update { it.copy(otp3 = otp) } }
    private fun updateOtp4(otp: String)  { _uiState.update { it.copy(otp4 = otp) } }
}
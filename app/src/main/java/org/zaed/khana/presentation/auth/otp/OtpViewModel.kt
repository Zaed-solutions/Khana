package org.zaed.khana.presentation.auth.otp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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


    private fun updateOtpError(message: PasswordFieldError) {
        _uiState.update {
            it.copy(
                otpError = message,
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


    fun handleUiState(it: OtpUIAction) {
        when (it) {
            is OtpUIAction.OnOtpChanged -> updateOtp(it.newOtp)
            OtpUIAction.OnResendOtpClicked -> TODO()
            OtpUIAction.OnVerifyOtpClicked -> TODO()
        }
    }



    private fun updateOtp(otp: String)  {
        _uiState.update {
            it.copy(
                otp = otp
            )
        }
    }




}
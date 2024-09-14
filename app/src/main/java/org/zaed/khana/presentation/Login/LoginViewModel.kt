package org.zaed.khana.presentation.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.auth.repository.AuthenticationRepository
import org.zaed.khana.data.auth.source.remote.model.User
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.EmailFieldError
import org.zaed.khana.data.util.PasswordFieldError
import org.zaed.khana.data.util.Result
import org.zaed.khana.presentation.Login.util.Validator

class LoginViewModel(
    private val authRepo: AuthenticationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    private fun getSignedInUser() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Success -> {
                        updateActionResult(AuthResults.LOGIN_SUCCESS)
                    }

                    is Result.Error -> {
                        updateActionResult(result.message)
                    }

                    is Result.Loading -> {
                        enableLoading()
                    }
                }
            }
        }
    }

    private fun updateActionResult(message: AuthResults) {
        println(message.userMessage)
        _uiState.update {
            it.copy(
                userMessage = message,
                loading = false
            )
        }
    }
    private fun updateEmailError(message: EmailFieldError) {
        _uiState.update {
            it.copy(
                emailError = message,
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

    fun onSignIn() {
        with(_uiState.value) {
            if (!Validator.EmailValidator.validateAll(email)) {
                viewModelScope.launch {
                    updateEmailError(EmailFieldError.INVALID_EMAIL)
                }
                return
            } else {
                updateActionResult(AuthResults.IDLE)
            }
            if (password.length < 6 || !Validator.PasswordValidator.validateAll(password)) {
                viewModelScope.launch {
                    updatePasswordError(PasswordFieldError.INVALID_PASSWORD)
                }
                return
            } else {
                updateActionResult(AuthResults.IDLE)
                updatePasswordError(PasswordFieldError.IDLE)
                updateEmailError(EmailFieldError.IDLE)
            }
        }
        viewModelScope.launch {
            with(_uiState.value) {
                authRepo.signInWithEmail(email, password).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            updateActionResult(AuthResults.LOGIN_SUCCESS)
                        }

                        is Result.Error -> {
                            updateActionResult(result.message)
                        }

                        is Result.Loading -> {
                            enableLoading()
                        }
                    }
                }
            }
        }
    }

    private fun enableLoading() {
        _uiState.update {
            it.copy(
                loading = true
            )
        }
    }
    fun onGoogleSignedIn(result: Result<AuthResult, AuthResults>) {

            result.onSuccessWithData { authResult ->
                updateActionResult(AuthResults.LOGIN_SUCCESS)
                viewModelScope.launch (Dispatchers.IO) {
                    authRepo.saveUser(authResult.user)
                }
                getSignedInUser()
            }.onFailure { error ->
                updateActionResult(error)
            }

    }

    fun handleUiState(it: LoginUIAction) {
        when(it){
            is LoginUIAction.OnEmailChanged -> updateEmail(it.newEmail)
            is LoginUIAction.OnPasswordChanged -> updatePassword(it.newPass)
            LoginUIAction.OnSignInClicked -> TODO()
        }
    }

    private fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }

    private fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email
            )
        }
    }


}
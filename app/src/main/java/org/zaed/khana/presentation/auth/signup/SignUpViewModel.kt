package org.zaed.khana.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.util.AuthResults
import org.zaed.khana.data.util.EmailFieldError
import org.zaed.khana.data.util.NameFieldError
import org.zaed.khana.data.util.PasswordFieldError
import org.zaed.khana.data.util.Result
import org.zaed.khana.data.util.TermsAndConditionsError
import org.zaed.khana.presentation.auth.util.Validator

class SignUpViewModel(
    private val authRepo: AuthenticationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()
    private fun getSignedInUser() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Success -> {
                        updateActionResult(AuthResults.SIGNUP_SUCCESS)
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

    private fun updateNameError(message: NameFieldError) {
        _uiState.update {
            it.copy(
                nameError = message,
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
    private fun updateTermsAndConditionsError(message: TermsAndConditionsError) {
        _uiState.update {
            it.copy(
                termsAndConditionsError = message,
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

    fun onSignUp() {
        with(_uiState.value) {
            if (!Validator.EmailValidator.validateAll(email)) {
                viewModelScope.launch {
                    updateEmailError(EmailFieldError.INVALID_EMAIL)
                }
                return
            } else {
                updateActionResult(AuthResults.IDLE)
                updateEmailError(EmailFieldError.IDLE)
            }
            if (password.length < 6 || !Validator.PasswordValidator.validateAll(password)) {
                viewModelScope.launch {
                    updatePasswordError(PasswordFieldError.INVALID_PASSWORD)
                }
                return
            } else {
                updateActionResult(AuthResults.IDLE)
                updatePasswordError(PasswordFieldError.IDLE)
            }
            if (name.length < 3 || !Validator.NameValidator.validateAll(name)) {
                viewModelScope.launch {
                    updateNameError(NameFieldError.INVALID_NAME)
                }
                return
            } else {
                updateActionResult(AuthResults.IDLE)
                updateNameError(NameFieldError.IDLE)
            }
            if (!termsAndConditions) {
                viewModelScope.launch {
                    updateTermsAndConditionsError(TermsAndConditionsError.TERMS_AND_CONDITIONS_NOT_ACCEPTED)
                }
                return
            }else{
                updateActionResult(AuthResults.IDLE)
                updateTermsAndConditionsError(TermsAndConditionsError.IDLE)
            }

        }
        viewModelScope.launch {
            with(_uiState.value) {
                authRepo.signUpWithEmail(name, email, password).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            updateActionResult(AuthResults.SIGNUP_SUCCESS)
                            viewModelScope.launch(Dispatchers.IO) {
                                authRepo.saveUser(result.data)
                            }
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

    fun signUpWithProvider(result: Result<AuthResult, AuthResults>) {
        result.onSuccessWithData { authResult ->
            updateActionResult(AuthResults.LOGIN_SUCCESS)
            viewModelScope.launch(Dispatchers.IO) {
                authRepo.saveUser(authResult.user)
            }
            getSignedInUser()
        }.onFailure { error ->
            updateActionResult(error)
        }
    }

    fun handleUiState(it: SignUpUIAction) {
        when (it) {
            is SignUpUIAction.OnEmailChanged -> updateEmail(it.newEmail)
            is SignUpUIAction.OnNameChanged -> updateName(it.newName)
            is SignUpUIAction.OnPasswordChanged -> updatePassword(it.newPass)
            is SignUpUIAction.OnSignUpClicked -> onSignUp()
            is SignUpUIAction.OnTermsAndConditionsClicked -> updateTermsAndConditions(it.approved)
        }
    }

    private fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }
    private fun updateName(name: String) {
        _uiState.update {
            it.copy(
                name = name
            )
        }
    }
    private fun updateTermsAndConditions(approved: Boolean) {
        _uiState.update {
            it.copy(
                termsAndConditions = approved
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
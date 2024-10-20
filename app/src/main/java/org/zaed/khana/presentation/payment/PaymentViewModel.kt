package org.zaed.khana.presentation.payment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.repository.OrderRepository

class PaymentViewModel(
    private val authRepo: AuthenticationRepository,
    private val orderRepository: OrderRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState = _uiState.asStateFlow()
    fun init(orderId: String){
        fetchCurrentUser()
        _uiState.update { it.copy(orderId = orderId) }
    }
    private fun fetchCurrentUser(){
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.update { it.copy(currentUser = user) }
            }.onFailure {
                Log.e("PaymentViewModel", "fetchCurrentUser: $it")
            }
        }
    }

    fun handleAction(action: PaymentUiAction) {
        when(action){
            is PaymentUiAction.OnPaymentMethodSelected -> updatePaymentMethod(action.paymentMethod)
            PaymentUiAction.OnContinueClicked -> confirmPayment()
            else -> Unit
        }
    }

    private fun confirmPayment() {
        viewModelScope.launch {
            orderRepository.confirmPayment(uiState.value.orderId, uiState.value.selectedPaymentMethod).onSuccess {
                _uiState.update { it.copy(isPaymentConfirmed = true) }
            }.onFailure {
                Log.e("PaymentViewModel", "confirmPayment: $it")
            }
        }
    }

    private fun updatePaymentMethod(paymentMethod: String) {
        _uiState.update { it.copy(selectedPaymentMethod = paymentMethod) }
    }
}